package com.villejuif.fdjfrontparissportifs.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(), DetailsContract.View {

    private lateinit var viewDataBinding: FragmentDetailsBinding

    private lateinit var mPresenter: DetailsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = DetailsPresenter(this)
        viewDataBinding = FragmentDetailsBinding.inflate(inflater,
            container, false).apply { detailspresenter = mPresenter }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        arguments?.getString("teamName")?.let {
            mPresenter.searchTeam(it, arguments?.getString("idTeam"))
        }

        setupNavigation()
    }

    override fun onTeam() {

    }

    private fun setupNavigation() {

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUpJob()
    }
}