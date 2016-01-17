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


    // TODO: Modificate all class;
//    public interface Movies {
//        TABLENAME///
//        COLUMNNAME
//                COLUMNRATING///
//    }

    private static MovieDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "movieDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_MOVIES = "movies";

    // Movies Table Columns
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TITLE = "title";
    private static final String MOVIE_BACKDROP = "backdrop_path";
    private static final String MOVIE_POSTER = "poster_path";
    private static final String MOVIE_POPULARITY = "popularity";
    private static final String MOVIE_VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_RELEASE_DATE = "release_date";
    private static final String MOVIE_OVERVIEW = "overview";

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
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES +
                "(" +
                MOVIE_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                MOVIE_TITLE + " TEXT, " +
                MOVIE_BACKDROP + " TEXT, " +
                MOVIE_POSTER + " TEXT, " +
                MOVIE_POPULARITY + " REAL, " +
                MOVIE_VOTE_AVERAGE + " REAL, " +
                MOVIE_RELEASE_DATE + " TEXT, " +
                MOVIE_OVERVIEW + " TEXT" +
                ")";

        db.execSQL(CREATE_MOVIES_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
            onCreate(db);
        }
    }

    public void addMovie(Movie movie){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, Integer.parseInt(movie.getId()));
            values.put(MOVIE_TITLE, movie.getTitle());
            values.put(MOVIE_POPULARITY, movie.getPopularity());
            values.put(MOVIE_BACKDROP, movie.getBackdrop_path());
            values.put(MOVIE_POSTER, movie.getPoster_path());
            values.put(MOVIE_VOTE_AVERAGE, movie.getVote_average());
            values.put(MOVIE_RELEASE_DATE, movie.getRelease_date());
            values.put(MOVIE_OVERVIEW, movie.getOverview());

            db.insertOrThrow(TABLE_MOVIES, null, values);
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
            // TODO: 15.01.16 extract all queries to another class as constants
            MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_ORDER_BY_s , TABLE_MOVIES, MOVIE_VOTE_AVERAGE);
        }else {
            MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s , TABLE_MOVIES);
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(MOVIE_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                int count = 0;
                do {
                    // TODO: 15.01.16 magic constants
                    if(count < ((page) * PAGE_START_INDEX)) continue;
                    if (count > ((page) * PAGE_END_INDEX)) break;
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

    public List<Movie> getMoviesByPopularity(int page){
        return getMovies(page, true);
    }

    public List<Movie> getMoviesToDescribe(int page){
        return getMovies(page, false);
    }

    public List<Movie> serchByTitle(String query){
        List<Movie> movies = new ArrayList<>();
        String MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_WHERE_s_LIKE_s, TABLE_MOVIES, MOVIE_TITLE, query+'%');

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
        String MOVIE_SELECT_QUERY = String.format(Queries.QUERIE_SELECT_ALL_FROM_s_WHERE_s_LIKE_s, TABLE_MOVIES, MOVIE_ID, id);

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
                .setId(String.valueOf(cursor.getInt(cursor.getColumnIndex(MOVIE_ID))))
                .setBackdrop_path(cursor.getString(cursor.getColumnIndex(MOVIE_BACKDROP)))
                .setPopularity(cursor.getDouble(cursor.getColumnIndex(MOVIE_POPULARITY)))
                .setPoster_path(cursor.getString(cursor.getColumnIndex(MOVIE_POSTER)))
                .setTitle(cursor.getString(cursor.getColumnIndex(MOVIE_TITLE)))
                .setVote_average(cursor.getDouble(cursor.getColumnIndex(MOVIE_VOTE_AVERAGE)))
                .setRelease_date(cursor.getString(cursor.getColumnIndex(MOVIE_RELEASE_DATE)))
                .setOverview(cursor.getString(cursor.getColumnIndex(MOVIE_OVERVIEW)));
    }
}