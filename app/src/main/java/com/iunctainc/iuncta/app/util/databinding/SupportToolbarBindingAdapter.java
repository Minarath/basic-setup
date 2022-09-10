package com.iunctainc.iuncta.app.util.databinding;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@BindingMethods({
        @BindingMethod(type = Toolbar.class, attribute = "onMenuItemClick", method = "setOnMenuItemClickListener"),
        @BindingMethod(type = Toolbar.class, attribute = "onNavigationClick", method = "setNavigationOnClickListener"),
})
public class SupportToolbarBindingAdapter {
}
