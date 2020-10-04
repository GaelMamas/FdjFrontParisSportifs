package com.villejuif.fdjfrontparissportifs.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.data.model.Team
import com.villejuif.fdjfrontparissportifs.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity(), DetailsContract.View {

    private lateinit var mPresenter: DetailsPresenter

    private lateinit var viewDataBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = DetailsPresenter(this)

        viewDataBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_details)

        val teamObserver = Observer<Team?>{
            viewDataBinding.detailspresenter = mPresenter
        }

        mPresenter.team.observe(this, teamObserver)

        intent?.getStringExtra(EXTRA_STRING_1)?.let {
            mPresenter.searchTeam(it, intent?.getStringExtra(EXTRA_STRING_2))
            title = it
        }
    }

    override fun onTeam() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUpJob()
    }

    companion object{
        const val EXTRA_STRING_1 = "teamName"
        const val EXTRA_STRING_2 = "idTeam"
    }
}