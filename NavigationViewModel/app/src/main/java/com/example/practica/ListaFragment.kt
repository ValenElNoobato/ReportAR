package com.example.practica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica.databinding.FragmentListaBinding
import com.example.practica.databinding.ItemListaBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFragment : Fragment() {

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.items.collect { lista ->

                    val adapter = ItemAdapter(lista) { item ->

                        val action =
                            ListaFragmentDirections
                                .actionListaFragmentToDetalleFragment(item.id)

                        findNavController().navigate(action)
                    }

                    binding.recyclerView.layoutManager =
                        LinearLayoutManager(requireContext())

                    binding.recyclerView.adapter = adapter
                }
            }
        }

        binding.buttonRecargar.setOnClickListener {
            viewModel.recargar()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ItemAdapter(
        private val lista: List<Item>,
        private val onClick: (Item) -> Unit
    ) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

        inner class ViewHolder(val binding: ItemListaBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(item: Item) {
                binding.textTitulo.text = item.titulo
                binding.textDescripcion.text = item.descripcion

                binding.root.setOnClickListener {
                    onClick(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemListaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(lista[position])
        }

        override fun getItemCount(): Int = lista.size
    }
}