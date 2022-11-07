package com.triget.application.server.service;

import com.mongodb.lang.Nullable;
import com.triget.application.server.controller.dto.LikeProductList;
import com.triget.application.server.controller.dto.ProductResponse;
import com.triget.application.server.domain.airport.Airport;
import com.triget.application.server.domain.journey.Journey;
import com.triget.application.server.domain.product.Accommodation;
import com.triget.application.server.domain.product.Attraction;
import com.triget.application.server.domain.product.Restaurant;
import com.triget.application.server.domain.product.flight.Flight;
import com.triget.application.server.domain.schedule.DailySchedule;
import com.triget.application.server.domain.schedule.Schedule;
import com.triget.application.server.entity.ProductSet;
import com.triget.application.server.repository.airport.AirportRepository;
import com.triget.application.server.repository.schedule.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ScheduleService {
    private final JourneyService journeyService;
    private final ProductService productService;
    private final FlightService flightService;
    private final AirportService airportService;
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ProductService productService, FlightService flightService, AirportRepository airportRepository, JourneyService journeyService, AirportService airportService, ScheduleRepository scheduleRepository) {
        this.productService = productService;
        this.flightService = flightService;
        this.journeyService = journeyService;
        this.airportService = airportService;
        this.scheduleRepository = scheduleRepository;
    }

    public ProductSet getAllProducts(LikeProductList likeProductList) {
        return new ProductSet(
                flightService.findById(likeProductList.getFlightId()),
                new ArrayList<>(likeProductList.getAccommodationIds().stream().map(
                        productService::findAccommodationById
                ).toList()),
                new ArrayList<>(likeProductList.getRestaurantIds().stream().map(
                        productService::findRestaurantById
                ).toList()),
                new ArrayList<>(likeProductList.getAttractionIds().stream().map(
                        productService::findAttractionById
                ).toList())
        );
    }

    public int getNumPerItem(@Nullable LocalDateTime start, @Nullable LocalDateTime end) {
        int itemNum = 3;
        int breakfastHour = 9;
        int lunchHour = 13;
        int dinnerHour = 18;
        if(start != null){
            int startHour = start.getHour();
            if(startHour > dinnerHour){
                itemNum = 0;
            } else if (startHour> lunchHour) {
                itemNum = 1;
            } else if (startHour > breakfastHour) {
                itemNum = 2;
            }
        }
        if(end != null) {
            int endHour = end.getHour();
            if(endHour < breakfastHour){
                itemNum -= 3;
            } else if (endHour < lunchHour) {
                itemNum -= 2;
            } else if (endHour < dinnerHour) {
                itemNum -= 1;
            }
        }
        return Math.max(itemNum, 0);
    }

    private float getDistance(float x, float y, float targX, float targY) {
        return (float) (Math.pow(targX-x, 2)+Math.pow(targY-y, 2));
    }

    private Accommodation getClosestAccommodation(float targX, float targY, List<Accommodation> accommodations) {
        Accommodation closest = accommodations.get(0);
        float closestDist = getDistance(closest.getLatitude(), closest.getLongitude(), targX, targY);
        for(Accommodation accommodation: accommodations)  {
            float x = accommodation.getLatitude();
            float y = accommodation.getLongitude();
            float tmpDist = getDistance(x, y, targX, targY);
            if(tmpDist < closestDist) {
                closest = accommodation;
                closestDist = tmpDist;
            }
        }
        return closest;
    }

    private Restaurant getClosestRestaurant(float targX, float targY, List<Restaurant> restaurants) {
        Restaurant closest = restaurants.get(0);
        float closestDist = getDistance(closest.getLatitude(), closest.getLongitude(), targX, targY);
        for (Restaurant restaurant : restaurants) {
            float x = restaurant.getLatitude();
            float y = restaurant.getLongitude();
            float tmpDist = getDistance(x, y, targX, targY);
            if(tmpDist < closestDist) {
                closest = restaurant;
                closestDist = tmpDist;
            }
        }
        return closest;
    }

    private Attraction getClosestAttraction(float targX, float targY, List<Attraction> attractions) {
        Attraction closest = attractions.get(0);
        float closestDist = getDistance(closest.getLatitude(), closest.getLongitude(), targX, targY);
        for (Attraction attraction : attractions) {
            float x = attraction.getLatitude();
            float y = attraction.getLongitude();
            float tmpDist = getDistance(x, y, targX, targY);
            if(tmpDist < closestDist) {
                closest = attraction;
                closestDist = tmpDist;
            }
        }
        return closest;
    }

    private Accommodation getNextAccommodation(float targX, float targY, List<Accommodation> accommodations, List<Accommodation> visited) {
        if(accommodations.size() == 0) {
            return getClosestAccommodation(targX, targY, visited);
        }
        Accommodation accommodation = getClosestAccommodation(targX, targY, accommodations);
        accommodations.remove(accommodation);
        accommodations.add(accommodation);
        return accommodation;
    }

    private Restaurant getNextRestaurant(float targX, float targY, List<Restaurant> restaurants, List<Restaurant> visited) {
        if(restaurants.size() == 0) {
            return getClosestRestaurant(targX, targY, visited);
        }
        Restaurant restaurant =  getClosestRestaurant(targX, targY, restaurants);
        restaurants.remove(restaurant);
        visited.add(restaurant);
        return restaurant;
    }

    private Attraction getNextAttraction(float targX, float targY, List<Attraction> attractions, List<Attraction> visited) {
        if(attractions.size() == 0) {
            return getClosestAttraction(targX, targY, visited);
        }
        Attraction attraction = getClosestAttraction(targX, targY, attractions);
        attractions.remove(attraction);
        visited.add(attraction);
        return attraction;
    }

    public List<ProductResponse> setFirstDay(ProductSet result, ProductSet visited) {
        List<ProductResponse> vertices = new ArrayList<>();
        Flight flight = result.getFlight();
        Airport airport = airportService.findByIata(flight.getLegs().get(0).getDestination());
        LocalDateTime startTime = LocalDateTime.parse(flight.getLegs().get(0).getArrival());
        int itemNum = getNumPerItem(startTime, null);
        for(int i = 0; i<itemNum; i++) {
            Restaurant tmpRestaurant = getNextRestaurant(
                    airport.getLatitude(),
                    airport.getLongitude(),
                    result.getRestaurants(),
                    visited.getRestaurants()
            );
            vertices.add(new ProductResponse(tmpRestaurant));
            Attraction attraction =  getNextAttraction(
                    tmpRestaurant.getLatitude(),
                    tmpRestaurant.getLongitude(),
                    result.getAttractions(),
                    visited.getAttractions()
            );
            vertices.add(new ProductResponse(attraction));
        }
        Accommodation accommodation = getNextAccommodation(
                vertices.get(vertices.size()-1).getLatitude(),
                vertices.get(vertices.size()-1).getLongitude(),
                result.getAccommodations(),
                visited.getAccommodations()
        );
        vertices.add(new ProductResponse(accommodation));
        return vertices;
    }

    public List<ProductResponse> setDailySchedule(ProductSet result, ProductSet visited, ProductResponse startPoint) {
        List<ProductResponse> vertices = new ArrayList<>();
        int itemNum = 3;
        for(int i=0; i<itemNum; i++){
            Restaurant restaurant = getNextRestaurant(
                    startPoint.getLatitude(),
                    startPoint.getLongitude(),
                    result.getRestaurants(),
                    visited.getRestaurants()
            );
            vertices.add(new ProductResponse(restaurant));
            Attraction attraction = getNextAttraction(
                    restaurant.getLatitude(),
                    restaurant.getLongitude(),
                    result.getAttractions(),
                    visited.getAttractions()
            );
            vertices.add(new ProductResponse(attraction));
        }
        Accommodation accommodation = getNextAccommodation(
                vertices.get(vertices.size()-1).getLatitude(),
                vertices.get(vertices.size()-1).getLongitude(),
                result.getAccommodations(),
                visited.getAccommodations()
        );
        vertices.add(new ProductResponse(accommodation));
        return vertices;
    }

    public List<ProductResponse> setLastDay(ProductSet result, ProductSet visited, ProductResponse startPoint) {
        LocalDateTime endTime = LocalDateTime.parse(result.getFlight().getLegs().get(1).getDeparture());
        List<ProductResponse> vertices = new ArrayList<>();
        int itemNum = getNumPerItem(null, endTime);
        for(int i=0; i<itemNum; i++){
            Restaurant restaurant = getNextRestaurant(
                    startPoint.getLatitude(),
                    startPoint.getLongitude(),
                    result.getRestaurants(),
                    visited.getRestaurants()
            );
            vertices.add(new ProductResponse(restaurant));
            Attraction attraction = getNextAttraction(
                    restaurant.getLatitude(),
                    restaurant.getLongitude(),
                    result.getAttractions(),
                    visited.getAttractions()
            );
            vertices.add(new ProductResponse(attraction));
        }
        return vertices;
    }

    public Schedule createSchedule(LikeProductList likeProductList) {
        Journey journey = journeyService.findById(likeProductList.getJourneyId());
        ProductSet result = getAllProducts(likeProductList);
        ProductSet visited = new ProductSet();

        LocalDate departureDate = LocalDate.parse(journey.getDepartureDate());
        LocalDate arrivalDate = LocalDate.parse(journey.getArrivalDate());
        int journeyPeriod = arrivalDate.compareTo(departureDate);

        List<DailySchedule> schedules = new ArrayList<>();

        List<ProductResponse> vertices = setFirstDay(result, visited);
        schedules.add(DailySchedule.builder()
                .order(0)
                .vertices(vertices)
                .edges(null)
                .build()
        );
        ProductResponse startPoint = vertices.get(vertices.size()-1);
        for(int i = 1; i < journeyPeriod-1; i++){
            vertices = setDailySchedule(result, visited, startPoint);
            schedules.add(DailySchedule.builder()
                    .order(i)
                    .vertices(vertices)
                    .edges(null)
                    .build()
            );
            startPoint  = vertices.get(vertices.size()-1);
        }
        vertices = setLastDay(result, visited, startPoint);
        schedules.add(DailySchedule.builder()
                .order(journeyPeriod-1)
                .vertices(vertices)
                .edges(null)
                .build()
        );
        return Schedule.builder()
                .userId(null)
                .journeyId(likeProductList.getJourneyId())
                .schedules(schedules)
                .build();
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }
}
