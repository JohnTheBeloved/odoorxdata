/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 * <p/>
 * Created on 21/4/15 4:01 PM
 *
 */
package com.ehealthinformatics.odoorxcore.core.rpc;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.ehealthinformatics.odoorxcore.core.rpc.handler.OdooVersionException;
import com.ehealthinformatics.odoorxcore.core.rpc.helper.OdooSession;
import com.ehealthinformatics.odoorxcore.core.rpc.helper.OdooVersion;
import com.ehealthinformatics.odoorxcore.core.rpc.listeners.IOdooConnectionListener;
import com.ehealthinformatics.odoorxcore.core.rpc.listeners.IOdooLoginCallback;
import com.ehealthinformatics.odoorxcore.core.rpc.wrapper.OdooWrapper;
import com.ehealthinformatics.odoorxcore.core.support.OUser;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class Odoo extends OdooWrapper<Odoo> {
    public static final String TAG = Odoo.class.getSimpleName();
    public static Boolean DEBUG = false;
    public static Integer REQUEST_TIMEOUT_MS = DefaultRetryPolicy.DEFAULT_TIMEOUT_MS;
    public static Integer DEFAULT_MAX_RETRIES = DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

    public enum ErrorCode {
        OdooVersionError(700),
        InvalidURL(404),
        OdooServerError(200),
        UnknownError(703),
        AuthenticationFail(401);

        int code;

        ErrorCode(int code) {
            this.code = code;
        }

        public int get() {
            return this.code;
        }

    }

    public Odoo(Context context, String baseURL) {
        super(context, baseURL);
    }


    public static OUser quickConnect(Context context, String url, String username,
                                     String password, String database) throws OdooVersionException {
        Odoo odoo = new Odoo(context, url).connect(true);
        return odoo.authenticate(username, password, database);
    }


    public static void quickConnect(Context context, String url, String username, String password,
                                    String database, IOdooLoginCallback callback)
            throws OdooVersionException {
        Odoo odoo = createInstance(context, url);
        odoo.authenticate(username, password, database, callback);
    }

    public static Odoo createInstance(Context context, String baseURL) throws OdooVersionException {
        return new Odoo(context, baseURL).connect(false);
    }


    public static Odoo createQuickInstance(Context context, String baseURL)
            throws OdooVersionException {
        return new Odoo(context, baseURL).connect(true);
    }

    public Odoo setOnConnect(IOdooConnectionListener callback) {
        mIOdooConnectionListener = callback;
        return this;
    }

    public OdooVersion getVersion() {
        return mVersion;
    }

    public OUser getUser() {
        return user;
    }

    public OdooSession getSession() {
        return odooSession;
    }

    @Override
    public String toString() {
        return "Odoo{" +
                serverURL + ": " +
                mVersion +
                '}';
    }

    public static boolean isHostAvailable(final String url, int timeout) throws IOException {
        URL u = new URL(url);
        String host = u.getHost();
        int port = u.getPort() == -1 ? u.getDefaultPort() : u.getPort();
        return isHostAvailable(host, port, timeout);
    }


    public static boolean isHostAvailable(final String host, final int port, final int timeout) throws IOException {
        final Socket socket = new Socket();
        try {
            final InetAddress inetAddress = InetAddress.getByName(host);
            final InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, port);

            socket.connect(inetSocketAddress, timeout);
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (socket != null) {
                socket.close(); // this will throw another exception... just let the function throw it
            }
        }
    }
}
