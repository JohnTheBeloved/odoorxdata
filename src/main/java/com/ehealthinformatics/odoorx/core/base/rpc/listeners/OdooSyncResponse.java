/**
 * Odoo, Open Source Management Solution
 * Copyright (C) 2012-today Odoo SA (<http:www.odoo.com>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http:www.gnu.org/licenses/>
 *
 * Created on 29/4/15 12:12 PM
 */
package com.ehealthinformatics.odoorx.core.base.rpc.listeners;

import com.ehealthinformatics.odoorx.core.base.rpc.helper.utils.gson.OdooResponse;

public class OdooSyncResponse {

    private OdooResponse response = null;
    private Object object;
    private Object error;

    public void setObject(Object obj) {
        object = obj;
    }

    public Object getObject() {
        return object;
    }

    public void setResponse(OdooResponse response) {
        this.response = response;
    }

    public OdooResponse get() {
        return response;
    }

    public void setError(Object obj) {
        error = obj;
    }

    public Object getError() {
        return error;
    }

    @Override
    public String toString() {
        return "OdooSyncResponse{" +
                "response=" + response +
                ", object=" + object +
                ", error=" + error + //TODO: make error concrete
                '}';
    }
}
