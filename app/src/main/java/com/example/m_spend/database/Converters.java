package com.example.m_spend.database;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {
  @TypeConverter
  public static Long fromDate(Date date){
    if (date == null) {
      return(null);
    }
    return date.getTime();
  }
  @TypeConverter
  public static Date toDate(Long millisSinceEpoch){
    if (millisSinceEpoch == null) {
      return(null);
    }
    return (new Date(millisSinceEpoch));
  }
}