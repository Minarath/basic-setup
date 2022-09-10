package com.iunctainc.iuncta.app.ui.main.editprofile

import android.content.Context
import android.content.Intent
import com.iunctainc.iuncta.app.R
import com.iunctainc.iuncta.app.databinding.ActivityEditprofileBinding
import com.iunctainc.iuncta.app.di.base.view.AppActivity

class EditProfileActivity : AppActivity<ActivityEditprofileBinding, EditProfileActivityVM>() {

    companion object {
        const val TAG = "EditProfileActivity"
    }

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, EditProfileActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        return intent
    }

    override fun getBindingActivity(): BindingActivity<EditProfileActivityVM> {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        return BindingActivity(R.layout.activity_editprofile, EditProfileActivityVM::class.java)
    }

    override fun subscribeToEvents(vm: EditProfileActivityVM) {

    }

}
