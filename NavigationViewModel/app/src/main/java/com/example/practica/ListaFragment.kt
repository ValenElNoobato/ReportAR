package com.example.practica

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica.databinding.FragmentListaBinding
import com.example.practica.databinding.ItemListaBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListaFragment : Fragment() {

    data class Item(
        val id: Int,
        val titulo: String,
        val descripcion: String
    )

    private var _binding: FragmentListaBinding? = null
    private val binding get() = _binding!!

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

        val lista = listOf(
            Item(1, "Elemento 1", "Descripción 1"),
            Item(2, "Elemento 2", "Descripción 2"),
            Item(3, "Elemento 3", "Descripción 3"),
            Item(4, "Elemento 4", "Descripción 4")
        )

        val adapter = ItemAdapter(lista) { item ->

            val action =
                ListaFragmentDirections
                    .actionListaFragmentToDetalleFragment(item.id)

            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
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