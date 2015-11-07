package com.lewisjuggins.miband;

import java.util.UUID;

/**
 * Created by Lewis on 29/12/14.
 */
public class MiBandConstants
{
	public static final String MAC_ADDRESS_FILTER = "88:0F:10";

	public static final String BASE_UUID = "0000%s-0000-1000-8000-00805f9b34fb";

	public static final UUID UUID_SERVICE_MILI_SERVICE = UUID.fromString(String.format(BASE_UUID, "FEE0"));

	public static final UUID UUID_CHARACTERISTIC_DEVICE_INFO = UUID.fromString(String.format(BASE_UUID, "FF01"));

	public static final UUID UUID_CHARACTERISTIC_DEVICE_NAME = UUID.fromString(String.format(BASE_UUID, "FF02"));

	public static final UUID UUID_CHARACTERISTIC_NOTIFICATION = UUID.fromString(String.format(BASE_UUID, "FF03"));

	public static final UUID UUID_CHARACTERISTIC_USER_INFO = UUID.fromString(String.format(BASE_UUID, "FF04"));

	public static final UUID UUID_CHARACTERISTIC_CONTROL_POINT = UUID.fromString(String.format(BASE_UUID, "FF05"));

	public static final UUID UUID_CHARACTERISTIC_REALTIME_STEPS = UUID.fromString(String.format(BASE_UUID, "FF06"));

	public static final UUID UUID_CHARACTERISTIC_ACTIVITY_DATA = UUID.fromString(String.format(BASE_UUID, "FF07"));

	public static final UUID UUID_CHARACTERISTIC_FIRMWARE_DATA = UUID.fromString(String.format(BASE_UUID, "FF08"));

	public static final UUID UUID_CHARACTERISTIC_LE_PARAMS = UUID.fromString(String.format(BASE_UUID, "FF09"));

	public static final UUID UUID_CHARACTERISTIC_DATE_TIME = UUID.fromString(String.format(BASE_UUID, "FF0A"));

	public static final UUID UUID_CHARACTERISTIC_STATISTICS = UUID.fromString(String.format(BASE_UUID, "FF0B"));

	public static final UUID UUID_CHARACTERISTIC_BATTERY = UUID.fromString(String.format(BASE_UUID, "FF0C"));

	public static final UUID UUID_CHARACTERISTIC_TEST = UUID.fromString(String.format(BASE_UUID, "FF0D"));

	public static final UUID UUID_CHARACTERISTIC_SENSOR_DATA = UUID.fromString(String.format(BASE_UUID, "FF0E"));

	public static final UUID UUID_CHARACTERISTIC_PAIR = UUID.fromString(String.format(BASE_UUID, "FF0F"));

	public static final byte ALIAS_LEN = 0xa;

	public static final byte NOTIFY_AUTHENTICATION_FAILED = 0x6;

	public static final byte NOTIFY_AUTHENTICATION_SUCCESS = 0x5;

	public static final byte NOTIFY_CONN_PARAM_UPDATE_FAILED = 0x3;

	public static final byte NOTIFY_CONN_PARAM_UPDATE_SUCCESS = 0x4;

	public static final int NOTIFY_DEVICE_MALFUNCTION = 0xff;

	public static final byte NOTIFY_FIRMWARE_UPDATE_FAILED = 0x1;

	public static final byte NOTIFY_FIRMWARE_UPDATE_SUCCESS = 0x2;

	public static final byte NOTIFY_FITNESS_GOAL_ACHIEVED = 0x7;

	public static final byte NOTIFY_FW_CHECK_FAILED = 0xb;

	public static final byte NOTIFY_FW_CHECK_SUCCESS = 0xc;

	public static final byte NOTIFY_NORMAL = 0x0;

	public static final int NOTIFY_PAIR_CANCEL = 0xef;

	public static final byte NOTIFY_RESET_AUTHENTICATION_FAILED = 0x9;

	public static final byte NOTIFY_RESET_AUTHENTICATION_SUCCESS = 0xa;

	public static final byte NOTIFY_SET_LATENCY_SUCCESS = 0x8;

	public static final byte NOTIFY_STATUS_MOTOR_ALARM = 0x11;

	public static final byte NOTIFY_STATUS_MOTOR_AUTH = 0x13;

	public static final byte NOTIFY_STATUS_MOTOR_AUTH_SUCCESS = 0x15;

	public static final byte NOTIFY_STATUS_MOTOR_CALL = 0xe;

	public static final byte NOTIFY_STATUS_MOTOR_DISCONNECT = 0xf;

	public static final byte NOTIFY_STATUS_MOTOR_GOAL = 0x12;

	public static final byte NOTIFY_STATUS_MOTOR_NOTIFY = 0xd;

	public static final byte NOTIFY_STATUS_MOTOR_SHUTDOWN = 0x14;

	public static final byte NOTIFY_STATUS_MOTOR_SMART_ALARM = 0x10;

	public static final byte NOTIFY_STATUS_MOTOR_TEST = 0x16;

	public static final byte NOTIFY_UNKNOWN = -0x1;

	//public static final String UUID_CHARACTERISTIC_CONTROL_POINT = "00001542-0000-3512-2118-0009af100700";

	public static final String UUID_CHARACTERISTIC_FEATURE = "2A9E";

	public static final String UUID_CHARACTERISTIC_MEASUREMENT = "2A9D";

	public static final String UUID_SERVICE_WEIGHT_SCALE_SERVICE = "181D";

	public static final String UUID_SERVICE_WEIGHT_SERVICE = "00001530-0000-3512-2118-0009af100700";

	public static final byte MSG_CONNECTED = 0x0;

	public static final byte MSG_DISCONNECTED = 0x1;

	public static final byte MSG_CONNECTION_FAILED = 0x2;

	public static final byte MSG_INITIALIZATION_FAILED = 0x3;

	public static final byte MSG_INITIALIZATION_SUCCESS = 0x4;

	public static final byte MSG_STEPS_CHANGED = 0x5;

	public static final byte MSG_DEVICE_STATUS_CHANGED = 0x6;

	public static final byte MSG_BATTERY_STATUS_CHANGED = 0x7;

	//public static final  COMMAND_CONFIRM_ACTIVITY_DATA_TRANSFER_COMPLETE = 0xat;
	//
	//    public static final byte COMMAND_FACTORY_RESET = 0x9t;
	//
	//    public static final byte COMMAND_FETCH_DATA = 0x6t;
	//
	//    public static final byte COMMAND_GET_SENSOR_DATA = 0x12t
	//
	//    public static final byte COMMAND_REBOOT = 0xct
	//
	//    public static final byte COMMAND_SEND_FIRMWARE_INFO = 0x7t
	//
	//    public static final COMMAND_SEND_NOTIFICATION = 0x8t
	//
	//    public static final int COMMAND_SET_COLOR_THEME = et;
	//
	//    public static final COMMAND_SET_FITNESS_GOAL = 0x5t
	//
	//    public static final COMMAND_SET_REALTIME_STEP = 0x10t
	//
	//    public static final COMMAND_SET_REALTIME_STEPS_NOTIFICATION = 0x3t
	//
	//    public static final COMMAND_SET_TIMER = 0x4t
	//
	//    public static final COMMAND_SET_WEAR_LOCATION = 0xft
	//
	//    public static final COMMAND_STOP_MOTOR_VIBRATE = 0x13t
	//
	//    public static final COMMAND_STOP_SYNC_DATA = 0x11t
	//
	//    public static final COMMAND_SYNC = 0xbt
	//
	//    public static final CONNECTION_LATENCY_LEVEL_HIGH = 0x2t;
	//
	//    public static final CONNECTION_LATENCY_LEVEL_LOW = 0x0t;
	//
	//    public static final CONNECTION_LATENCY_LEVEL_MEDIUM = 0x1t;
	//
	//    public static final MODE_REGULAR_DATA_LEN_BYTE = 0x0t;
	//
	//    public static final MODE_REGULAR_DATA_LEN_MINITE = 0x1t
	//
	//    public static final PROFILE_STATE_AUTHENTICATION_FAILED:I = 0x4
	//
	//    public static final PROFILE_STATE_AUTHENTICATION_SUCCESS:I = 0x3
	//
	//    public static final PROFILE_STATE_INITIALIZATION_FAILED:I = 0x2
	//
	//    public static final PROFILE_STATE_INITIALIZATION_SUCCESS:I = 0x1
	//
	//    public static final PROFILE_STATE_UNKNOWN:I = 0x0
	//
	//    public static final TEST_DISCONNECTED_REMINDER = 0x5t
	//
	//    public static final TEST_NOTIFICATION = 0x3t
	//
	//    public static final TEST_REMOTE_DISCONNECT = 0x1t
	//
	//    public static final TEST_SELFTEST = 0x2t
}
