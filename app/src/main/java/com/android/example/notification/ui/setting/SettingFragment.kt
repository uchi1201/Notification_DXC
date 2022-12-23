package com.android.example.notification.ui.setting


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.example.notification.R
import com.android.example.notification.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val title = binding.titleSetting
        title.title.text = getString(com.android.example.notification.R.string.title_setting)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notification: Button = binding.notification
        notification.setOnClickListener {
            findNavController().navigate(R.id.action)
        }
        val categoryBtn: Button = binding.category
        categoryBtn.setOnClickListener{
            findNavController().navigate(R.id.category_action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}