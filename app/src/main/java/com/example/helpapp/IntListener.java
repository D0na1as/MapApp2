package com.example.helpapp;

import java.util.ArrayList;
import java.util.List;

public class IntListener {
    private static boolean myBoolean;
    private static boolean nodesConnetion;
    private static List<ConnectionBooleanChangedListener> listeners = new ArrayList<ConnectionBooleanChangedListener>();
    private static List<ConnectionBooleanChangedListener> nodeListeners = new ArrayList<ConnectionBooleanChangedListener>();

    public static boolean getMyBoolean() { return myBoolean; }
    public static boolean getNodesBoolean() { return nodesConnetion; }

    public static void setMyBoolean(boolean value) {
        myBoolean = value;

        for (ConnectionBooleanChangedListener l : listeners) {
            l.OnMyBooleanChanged();
        }
    }
    public static void setNodesBoolean(boolean value) {
        nodesConnetion = value;

        for (ConnectionBooleanChangedListener l : nodeListeners) {
            l.OnMyBooleanChanged();
        }
    }

    public static void addMyBooleanListener(ConnectionBooleanChangedListener l) {
        listeners.add(l);
    }
    public static void addNodeBooleanListener(ConnectionBooleanChangedListener l) {
        nodeListeners.add(l);
    }
}