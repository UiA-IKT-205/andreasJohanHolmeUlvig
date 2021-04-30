package com.example.superpiano

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.*
import kotlinx.android.synthetic.main.fragment_piano.view.*

class Piano : Fragment() {

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2")
    private val halfTones = listOf("C#","D#","F#","G#","A#","C#2","D#2","F#2","G#2")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()
        private val score:MutableList<Note> = mutableListOf<Note>

        fullTones.forEach{
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            fullTonePianoKey.onKeyDown = {
                println("piano key down $it")
            }

            fullTonePianoKey.onKeyUp = {
                println("piano key up $it")
            }
            ft.add(view.fullTonePianoKeys.id, fullTonePianoKey, "note_$it")

      //      ft.add(view.pianoKeys.id,fullTonePianoKey,"note $it")
        }
        halfTones.forEach { it ->
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)

            halfTonePianoKey.onKeyDown = { note ->
                println("Piano key down $note")
            }

            halfTonePianoKey.onKeyUp = { note ->
                println("Piano key up $note")
            }

            // View is possibly not instanced (possibly move to "onViewCreated")
            ft.add(view.halfToneKeysLayout.id, halfTonePianoKey, "note_$it")

        }
        ft.commit()

        return view
    }
}