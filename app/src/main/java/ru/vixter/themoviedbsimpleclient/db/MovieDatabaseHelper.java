package ru.vixter.themoviedbsimpleclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.vixter.themoviedbsimpleclient.model.themoviedb.Movie;

/**
 * Created by vixter on 17.01.16.
 */
public class MovieDatabaseHelper extends SQLiteOpenHelper {


    private static MovieDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "movieDatabase";
    private static final int DATABASE_VERSION = 1;

    public interface Movies {
        public static final String TABLE_NAME = "movies";
        public static final String _ID = "id";
        public static final String _TITLE = "title";
        public static final String _BACKDROP = "backdrop_path";
        public static final String _POSTER = "poster_path";
        public static final String _POPULARITY = "popularity";
        public static final String _VOTE_AVERAGE = "vote_average";
        public static final String _RELEASE_DATE = "release_date";
        public static final String _OVERVIEW = "overview";

    }

    private static final int PAGE_START_INDEX = 20;
    private static final int PAGE_END_INDEX = 40;




    public static synchronized MovieDatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MovieDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }


    public MovieDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + Movies.TABLE_NAME +
                "(" +
                Movies._ID + " INTEGER PRIMARY KEY," + // Define a primary key
                Movies._TITLE + " TEXT, " +
                Movies._BACKDROP + " TEXT, " +
                Movies._POSTER + " TEXT, " +
                Movies._POPULARITY + " REAL, " +
                Movies._VOTE_AVERAGE + " REAL, " +
                Movies._RELEASE_DATE + " TEXT, " +
                Movies._OVERVIEW + " TEXT" +
                ")";

        db.execSQL(CREATE_MOVIES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Movies.TABLE_NAME);
            onCreate(db);
        }
    }

    public void addMovie(Movie movie){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(Movies._ID, Integer.parseInt(movie.getId()));
            values.put(Movies._TITLE, movie.getTitle());
            values.put(Movies._POPULARITY, movie.getPopularity());
            values.put(Movies._BACKDROP, movie.getBackdrop_path());
            values.put(Movies._POSTER, movie.getPoster_path());
            values.put(Movies._VOTE_AVERAGE, movie.getVote_average());
            values.put(Movies._RELEASE_DATE, movie.getRelease_date());
            values.put(Movies._OVERVIEW, movie.getOverview());

            db.insertOrThrow(Movies.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public void addAllMovies(Collection<Movie> movieCollection){
        for(Movie m : movieCollection) addMovie(m);
    }

    private List<Movie> getMovies(int page, boolean byPopularity){

        List<Movie> movies = new ArrayList<>();
        String MOVIE_SELECT_QUERY = null;
        if(byPopularity){
            MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_ORDER_BY_s , Movies.TABLE_NAME, Movies._VOTE_AVERAGE);
        }else {
            MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s , Movies.TABLE_NAME);
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MOVIE_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                int count = 0;
                do {
                    // TODO: 15.01.16 magic constants
                    if(count < getStartItemPocition(page)) continue;
                    if (count > getEndItemPocition(page)) break;
                    movies.add(getMovieFromCursor(cursor));
                    count++;
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return movies;
    }

    private int getStartItemPocition(int page){
        return page * PAGE_START_INDEX;
    }

    private int getEndItemPocition(int page){
        return page * PAGE_END_INDEX;
    }

    public List<Movie> getMoviesByPopularity(int page){
        return getMovies(page, true);
    }

    public List<Movie> getMoviesToDescribe(int page){
        return getMovies(page, false);
    }

    public List<Movie> serchByTitle(String query){
        List<Movie> movies = new ArrayList<>();
        String MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_WHERE_s_LIKE_s, Movies.TABLE_NAME, Movies._TITLE, query+'%');

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MOVIE_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst())
                do {
                    movies.add(getMovieFromCursor(cursor));
                }while (cursor.moveToNext());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return movies;
    }

    public Movie getMovieItem(String id){
        Movie movie = null;
        String MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_WHERE_s_LIKE_s, Movies.TABLE_NAME, Movies._ID, id);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MOVIE_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                movie = getMovieFromCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return movie;
    }

    private Movie getMovieFromCursor(Cursor cursor) {
        return new Movie()
                .setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(Movies._ID))))
                .setBackdrop_path(cursor.getString(cursor.getColumnIndex(Movies._BACKDROP)))
                .setPopularity(cursor.getDouble(cursor.getColumnIndex(Movies._POPULARITY)))
                .setPoster_path(cursor.getString(cursor.getColumnIndex(Movies._POSTER)))
                .setTitle(cursor.getString(cursor.getColumnIndex(Movies._TITLE)))
                .setVote_average(cursor.getDouble(cursor.getColumnIndex(Movies._VOTE_AVERAGE)))
                .setRelease_date(cursor.getString(cursor.getColumnIndex(Movies._RELEASE_DATE)))
                .setOverview(cursor.getString(cursor.getColumnIndex(Movies._OVERVIEW)));
    }
}