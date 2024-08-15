package com.tinqinacademy.bff.api.restapiroutes;

public class RestApiRoutes {
    private static final String API_V1 = "/api/v1";
    //containing api_v1
    private static final String API_V1_HOTEL = API_V1 + "/hotel";
    private static final String API_V1_SYSTEM = API_V1 + "/system";
    //without api_v1
    private static final String ROOM_ID = "/{roomId}";
    private static final String REGISTER = "/register";
    private static final String ROOM_ROOM_ID = "/room/{roomId}";
    private static final String ROOM_ID_COMMENT = "/{roomId}/comment";
    private static final String COMMENT_COMMENT_ID = "/comment/{commentId}";
    //hotel
    public static final String HOTEL_GET_AVAILABLE_ROOMS = API_V1_HOTEL + "/rooms";
    public static final String HOTEL_GET_AVAILABLE_ROOMS_BY_ID = API_V1_HOTEL + ROOM_ID;
    public static final String HOTEL_BOOK_ROOM = API_V1_HOTEL + ROOM_ID;
    public static final String HOTEL_UNBOOK_ROOM = API_V1_HOTEL + "/{bookingId}";
    public static final String HOTEL_GET_COMMENTS = API_V1_HOTEL + ROOM_ID_COMMENT;
    public static final String HOTEL_POST_COMMENT = API_V1_HOTEL + ROOM_ID_COMMENT;
    public static final String HOTEL_UPDATE_COMMENT = API_V1_HOTEL + COMMENT_COMMENT_ID;
    //system
    public static final String SYSTEM_REGISTER_VISITOR = API_V1_SYSTEM + REGISTER;
    public static final String SYSTEM_REPORT_VISITOR_INFO = API_V1_SYSTEM + REGISTER;
    public static final String SYSTEM_CREATE_ROOM = API_V1_SYSTEM + "/room";
    public static final String SYSTEM_UPDATE_ROOM = API_V1_SYSTEM + ROOM_ROOM_ID;
    public static final String SYSTEM_UPDATE_ROOM_PARTIALLY = API_V1_SYSTEM + ROOM_ROOM_ID;
    public static final String SYSTEM_DELETE_ROOM = API_V1_SYSTEM + ROOM_ROOM_ID;
    public static final String SYSTEM_DELETE_COMMENT = API_V1_SYSTEM + COMMENT_COMMENT_ID;
    public static final String SYSTEM_EDIT_COMMENT = API_V1_SYSTEM + COMMENT_COMMENT_ID;
}
