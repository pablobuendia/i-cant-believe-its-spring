package com.pablo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class test {


  @Test
  public void testMethod() throws JSONException {
    JSONObject testObject = new JSONObject();
    testObject.put("Boolean", true);
    System.out.println(testObject.getBoolean("Boolean"));
  }

}
