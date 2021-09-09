package com.plugin.iconchange;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * This class echoes a string called from JavaScript.
 */
public class IconChangePlugin extends CordovaPlugin {
  private ComponentName defaultComponent;
  private ComponentName testComponent;
  private PackageManager packageManager;
  private String packageName;
  private CallbackContext callbackContext ;

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("changeIcon")) {
      try {
        String parameter = args.get(0) + "";
        JSONArray jaChangeComponent = new JSONArray(parameter);
        IconChangPlugin.this.callbackContext = callbackContext ;
        for (int i = 0; i < jaChangeComponent.length(); i++) {
          JSONObject joChangeComponent = (JSONObject) jaChangeComponent.get(i);
          changeAppIcon(joChangeComponent.getBoolean("isOn"), joChangeComponent.getString("name"));
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return true;
    }
    return false;
  }



  private void changeAppIcon(boolean isChangeIcon, String componetName) {
    PackageManager pm = cordova.getActivity().getPackageManager();
    componetName = cordova.getActivity().getPackageName() + "." + componetName;
    ComponentName normalComponentName = new ComponentName(
      cordova.getActivity(),
      componetName);
    int normalNewState = isChangeIcon == true ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
      : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;

    if (pm.getComponentEnabledSetting(normalComponentName) != normalNewState) {
      if (isChangeIcon){
        IconChangPlugin.this.callbackContext.success("Sucess - icon changed");
      }
      pm.setComponentEnabledSetting(
        normalComponentName,
        normalNewState,
        PackageManager.DONT_KILL_APP);
    }
  }
}
