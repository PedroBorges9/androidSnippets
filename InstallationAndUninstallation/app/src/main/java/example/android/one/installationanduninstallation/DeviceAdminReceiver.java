package example.android.one.installationanduninstallation;

import android.content.Context;
import android.content.Intent;


/**
 * Created by Pedro Borges on 10/12/15.
 */
public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver{
//    void showToast(Context context, String msg) {
//        String status = context.getString(R.string.admin_receiver_status, msg);
//        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
//    }
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.d("LOGTOXML", "RECEIVED IN ADMIN");
        if (intent.getAction() == ACTION_DEVICE_ADMIN_DISABLE_REQUESTED) {
            abortBroadcast();
        }
        super.onReceive(context, intent);
    }
    @Override
    public void onEnabled(Context context, Intent intent) {
//        showToast(context, context.getString(R.string.admin_receiver_status_enabled));
    }
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return context.getString(R.string.app_name);
    }
    @Override
    public void onDisabled(Context context, Intent intent) {
//        showToast(context, context.getString(R.string.admin_receiver_status_disabled));
    }
    @Override
    public void onPasswordChanged(Context context, Intent intent) {
//        showToast(context, context.getString(R.string.admin_receiver_status_pw_changed));
    }
    @Override
    public void onPasswordFailed(Context context, Intent intent) {
//        showToast(context, context.getString(R.string.admin_receiver_status_pw_failed));
    }
    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
//        showToast(context, context.getString(R.string.admin_receiver_status_pw_succeeded));
    }
    @Override
    public void onPasswordExpiring(Context context, Intent intent) {
//        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(
//                Context.DEVICE_POLICY_SERVICE);
//        long expr = dpm.getPasswordExpiration(
//                new ComponentName(context, DeviceAdminSampleReceiver.class));
//        long delta = expr - System.currentTimeMillis();
//        boolean expired = delta < 0L;
//        String message = context.getString(expired ?
//                R.string.expiration_status_past : R.string.expiration_status_future);
//        showToast(context, message);
//        Log.v("LOGTOXML", message);
    }
}
