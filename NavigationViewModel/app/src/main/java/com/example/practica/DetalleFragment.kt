package com.example.practica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.practica.databinding.FragmentDetalleBinding

class DetalleFragment : Fragment() {

    private val args: DetalleFragmentArgs by navArgs()
    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.itemId

        binding.textDetalle.text = "ID recibido: $id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}