package com.clt.perseal.WebSerivce;

import android.util.Log;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 * Created by clt_abc on 2017/5/17.
 */

public class WsControler {

    public static final String Tag="WSCONTROLER";


    public static String isActivateByPhone(String phone){


        //1  HttpTransportSE
        HttpTransportSE httpTransportSE = new HttpTransportSE(Const.WSConst.t01.URL);
        httpTransportSE.debug=true;

        //2 SoapObject
        SoapObject soapObject = new SoapObject(Const.WSConst.t01.nameSpace, Const.WSConst.t01.isActivateByPhone);
        //3  添加请求参数
        soapObject.addProperty("arg0", phone);
        //4 SoapSerializationEnvelope
        SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        soapSerializationEnvelope.bodyOut=soapObject;
//        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        soapSerializationEnvelope.dotNet= false;//这里如果设置为TRUE,那么在服务器端将获取不到参数值(如:将这些数据插入到数据库中的话)


        try {
            // 调用WebService
            httpTransportSE.call(null, soapSerializationEnvelope);
            String returnvalue ="";
            if(soapSerializationEnvelope.getResponse()!=null){
                SoapPrimitive primitive =  (SoapPrimitive) soapSerializationEnvelope.getResponse();
                Log.d(Tag, primitive.toString()+"");
                returnvalue=primitive.toString();
            }
            //已经激活返回ESSRET:0未激活返回ESSRET:1  手机号未传入返回ESSRET:phoneIsNull
            return  returnvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static String getAppUpdate(String appName,String appvVersion){

        //1  HttpTransportSE
        final HttpTransportSE httpTransportSE = new HttpTransportSE(Const.WSConst.t01.URL);
        httpTransportSE.debug=true;

        //2 SoapObject
        SoapObject soapObject = new SoapObject(Const.WSConst.t01.nameSpace, Const.WSConst.t01.getAppUpdate);
        //3  添加请求参数
        soapObject.addProperty("arg0", appName);
        soapObject.addProperty("arg1", appvVersion);
        //4 SoapSerializationEnvelope
        final SoapSerializationEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        soapSerializationEnvelope.bodyOut=soapObject;
//        soapSerializationEnvelope.setOutputSoapObject(soapObject);
        soapSerializationEnvelope.dotNet= false;//这里如果设置为TRUE,那么在服务器端将获取不到参数值(如:将这些数据插入到数据库中的话)

        try {
            // 调用WebService
            httpTransportSE.call(null, soapSerializationEnvelope);
            String returnvalue ="";
            if(soapSerializationEnvelope.getResponse()!=null){
                SoapPrimitive primitive =  (SoapPrimitive) soapSerializationEnvelope.getResponse();
                Log.d(Tag, primitive.toString()+"");
                returnvalue=primitive.toString();
            }
            return  returnvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
