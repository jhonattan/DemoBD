package com.example.android.demobd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ANDROID on 10/09/2016.
 */
public class DataBaseManager {

    public static final String CREATE_TABLE =
            "CREATE TABLE frases("
                    + "_id integer primary key autoincrement," // obligatorio _id
                    + "autor text not null,"
                    + "frase text not null);";


    public static final  String INSERT =
            "INSERT INTO frases(autor, frase) VALUES"
                    + "('Víctor Balta','No todo es blanco y negro'),"
                    + "('Ana Torres','El camino recto no siempre es el más corto'),"
                    + "('Jorge Risco','Todos los caminos llevan a Roma');";


    // operaciones CRUD
    SQLiteDatabase database;

    public DataBaseManager(Context context) {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }
    //

    public String frasesQry() {
        StringBuilder result = new StringBuilder();

        String[] cols = {"_id", "autor", "frase"};
        Cursor cursor = database.query("frases", cols, null, null, null, null, null);

        while(cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String autor = cursor.getString(1);
            String frase = cursor.getString(2);

            String fil = String.format("%02d %-20s\r\n %-40s", id, autor, frase);
            result.append(fil).append("\r\n\r\n");
        }

        return result.toString();
    }

    public void frasesIns(String autor, String frase) {
        ContentValues values = new ContentValues();

        values.put("autor", autor);
        values.put("frase", frase);

        long ok = database.insert("frases", null, values);
        // en el segundo parámetro va el nombre del campo que tiene valor null
        // si se pone null significa ningún campo
        // si ok == -1 no pudo hacer la inserción

        /*
        String sql = "INSERT INTO frases VALUES(" +
                "null," +
                "'" + autor + "'," +
                "'" + frase + "');";
        database.execSQL(sql);
        */
    }

    public void frasesDel(Integer id) {
        String[] param = {id.toString()};
        database.delete("frases", "_id = ?", param);

        /*
        String sql = "DELETE FROM frases WHERE _id=" + id;
        database.execSQL(sql);
        */
    }

    public void frasesUpd(String autor, String frase, Integer id) {
        ContentValues values = new ContentValues();
        values.put("autor", autor);
        values.put("frase", frase);

        String[] param = {id.toString()};

        database.update("frases", values, "_id = ?", param);

        /*
        String sql = "UPDATE frases SET frase='" + frase + "' WHERE _id=" + id;
        database.execSQL(sql);
        */
    }

}
