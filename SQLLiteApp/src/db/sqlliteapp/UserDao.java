package db.sqlliteapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;
import db.sqlliteapp.Users;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table Users.
*/
public class UserDao extends AbstractDao<Users, Long> {

    public static final String TABLENAME = "USERLISTS";
	

    /**
     * Properties of entity Users.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Email = new Property(2, String.class, "email", false, "EMAIL");
        public final static Property Address = new Property(3, String.class, "address", false, "ADDRESS");
        public final static Property City = new Property(4, String.class, "city", false, "CITY");
        public final static Property Pincode = new Property(5, String.class, "pincode", false, "PINCODE");
        public final static Property Comment = new Property(6, String.class, "comment", false, "COMMENT");
        public final static Property Date = new Property(7, java.util.Date.class, "date", false, "DATE");
    };


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifUsersxists) {
        String constraint = ifUsersxists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "+TABLENAME+ (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'EMAIL' TEXT NOT NULL ," + // 2: email
                "'ADDRESS' TEXT NOT NULL ," + // 3: address
                "'CITY' TEXT NOT NULL ," + // 4: city
                "'PINCODE' TEXT NOT NULL ," + // 5: pincode
                "'COMMENT' TEXT," + // 6: comment
                "'DATE' INTEGER);"); // 7: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'USERLISTS'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Users entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(3, email);
        }
        
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(4, address);
        }
        
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(5, city);
        }
        
        String pincode = entity.getEmail();
        if (pincode != null) {
            stmt.bindString(6, pincode);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(7, comment);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(8, date.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Users readEntity(Cursor cursor, int offset) {
        Users entity = new Users( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // email
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // address,
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // city
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // pincode
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // comment
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // date
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Users entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEmail(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAddress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCity(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPincode(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setComment(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Users entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Users entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }

	
    
}
