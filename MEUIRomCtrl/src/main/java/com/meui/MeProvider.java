package com.meui;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.database.sqlite.*;
import android.util.*;

public class MeProvider extends ContentProvider
{
	// 胡乱写的，自知只能混过编译，还须仔细查阅相关资料再来重写。
	public static final Uri CONTENT_URI = Uri.parse("content://com.meui.RomCtrl/BarColors");
	private final String STATUS_BAR_COLOR="BarColors";
	//private ContentResolver contentResolver;
	private MeDatabase database;
	private Context mContext;
	
	@Override
	public boolean onCreate()
	{
		
		mContext=getContext();
		//contentResolver=mContext.getContentResolver();
		database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
		//database
		return true;
	}

	@Override
	public Cursor query(Uri p1, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		if(database==null)database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
		
		final SQLiteDatabase db=database.getWritableDatabase();
		
		return db.query(STATUS_BAR_COLOR,projection,selection,selectionArgs,sortOrder,null,null);
	}

	@Override
	public String getType(Uri p1)
	{
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values)
	{
		if(database==null)database=new MeDatabase(mContext,STATUS_BAR_COLOR,null,1);
		
		SQLiteDatabase sqL=database.getWritableDatabase();
		long rowId = sqL.insert(STATUS_BAR_COLOR, null, values);
		 if(rowId > 0){
			 //判断插入是否执行成功
			 final Uri insertedUserUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
			 //通知监听器，数据已经改变
			 mContext.getContentResolver().notifyChange(insertedUserUri, null);
			 return insertedUserUri;
		 }
		 return uri;
	}

	@Override
	public int delete(Uri p1, String p2, String[] p3)
	{
		SQLiteDatabase sqL=database.getWritableDatabase();
		final int RESULT=sqL.delete(STATUS_BAR_COLOR,p2,p3);
		return RESULT;
	}

	@Override
	public int update(Uri p1, ContentValues p2, String p3, String[] p4)
	{
		// TODO: Implement this method
		final SQLiteDatabase sqL=database.getWritableDatabase();
		
		return sqL.update(STATUS_BAR_COLOR,p2,p3,p4);
		
	}
	
}
