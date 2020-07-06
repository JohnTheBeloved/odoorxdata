package com.ehealthinformatics.odoorx.core.data.dao;

import android.content.Context;

import com.ehealthinformatics.odoorx.core.base.orm.OModel;
import com.ehealthinformatics.odoorx.core.base.orm.fields.OColumn;
import com.ehealthinformatics.odoorx.core.base.orm.fields.types.OBoolean;
import com.ehealthinformatics.odoorx.core.base.orm.fields.types.OVarchar;
import com.ehealthinformatics.odoorx.core.base.support.OUser;

import static com.ehealthinformatics.odoorx.core.base.orm.fields.OColumn.RelationType.ManyToOne;

public class AccountMoveLineDao extends OModel {

    OColumn name = new OColumn ("Name", OVarchar.class);
    OColumn user_id = new OColumn (null, ResUsers.class, ManyToOne);
    OColumn is_difference_zero =  new OColumn ("Difference", OBoolean.class);

    public AccountMoveLineDao(Context context, OUser user) {
        super(context, "account.move.line", user);
    }

}
