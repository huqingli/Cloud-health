package com.moy.util;

import java.util.HashMap;

public class SampleGattAttributes
{
  public static String CLIENT_CHARACTERISTIC_CONFIG;
  public static String HEART_RATE_MEASUREMENT;
  private static HashMap<String, String> attributes = new HashMap();
  
  static
  {
    HEART_RATE_MEASUREMENT = "0000C004-0000-1000-8000-00805f9b34fb";
    CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    attributes.put("0000fff00000-1000-8000-00805f9b34fb", "Heart Rate Service");
    attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
    attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
    attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
  }
  
  public static String lookup(String paramString1, String paramString2)
  {
    paramString1 = (String)attributes.get(paramString1);
    if (paramString1 == null) {
      return paramString2;
    }
    return paramString1;
  }
}


/* Location:              C:\Users\hu\Desktop\ktool\dex2jar-2.0\classes-dex2jar.jar!\com\example\XueYaShuangMoShi\SampleGattAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */