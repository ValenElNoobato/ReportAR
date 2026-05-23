package com.example.reportar.presentation.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.reportar.R
import com.example.reportar.databinding.FragmentLoginBinding
import com.example.reportar.presentation.ui.userMenu.UserMenuFragment
import com.example.reportar.presentation.viewmodel.ProfileViewModel
import com.example.reportar.presentation.viewmodel.ProfileViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels {
        ProfileViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        observeState()

        binding.btnLogin.setOnClickListener {

            viewModel.login(
                binding.etUser.text.toString(),
                binding.etPassword.text.toString()
            )
        }
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.state.collect { state ->

                    if (state.isLogged) {

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.profileContainer, UserMenuFragment())
                            .commit()
                    }

                    state.error?.let {

                        Toast.makeText(
                            requireContext(),
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}