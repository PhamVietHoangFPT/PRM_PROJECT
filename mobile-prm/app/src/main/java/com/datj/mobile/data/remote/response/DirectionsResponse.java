package com.datj.mobile.data.remote.response;

import java.util.List;

public class DirectionsResponse {
    public List<Route> routes;

    public static class Route {
        public OverviewPolyline overviewPolyline;
    }

    public static class OverviewPolyline {
        public String points;

        public String getPoints() {
            return points;
        }
    }
}