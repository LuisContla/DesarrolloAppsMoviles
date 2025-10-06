package com.example.gameverse.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gameverse.databinding.SheetPoiBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PoiBottomSheet : BottomSheetDialogFragment() {

    private var _binding: SheetPoiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetPoiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.txtTitle.text = requireArguments().getString(ARG_TITLE)
        binding.txtDesc.text = requireArguments().getString(ARG_DESC)
        val imgRes = requireArguments().getInt(ARG_IMAGE_RES)
        if (imgRes != 0) binding.imgThumb.setImageResource(imgRes)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_DESC = "desc"
        private const val ARG_IMAGE_RES = "imageRes"

        fun newInfo(title: String, desc: String, imageRes: Int) = PoiBottomSheet().apply {
            arguments = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_DESC, desc)
                putInt(ARG_IMAGE_RES, imageRes)
            }
        }
    }
}
