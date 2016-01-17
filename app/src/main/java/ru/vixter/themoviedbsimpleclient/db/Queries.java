package ru.vixter.themoviedbsimpleclient.db;

/**
 * Created by vixter on 17.01.16.
 */
public class Queries {

    public static final String QUERIE_SELECT_ALL_FROM_s_ORDER_BY_s = "SELECT * FROM %s ORDER BY %s DESC";
    public static final String QUERIE_SELECT_ALL_FROM_s = "SELECT * FROM %s";
    public static final String QUERIE_SELECT_ALL_FROM_s_WHERE_s_LIKE_s = "SELECT * FROM %s WHERE %s LIKE \'%s\'";

}
