package com.example.superpiano

import android.os.Bundle
import android.os.SystemClock.uptimeMillis
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.*
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream

class Piano : Fragment() {

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C","D","E","F","G","A","B","C2","D2","E2","F2","G2")
    private val halfTones = listOf("C#","D#","F#","G#","A#","C#2","D#2","F#2","G#2")
    private val score:MutableList<Note> = mutableListOf<Note>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        var scoreStartTime = uptimeMillis()

        fullTones.forEach{
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)
            var startPlay:Long = 0

            fullTonePianoKey.onKeyDown = { note ->
                startPlay = uptimeMillis()
                println("piano key down $it")
            }

            fullTonePianoKey.onKeyUp = { note ->
                val endPlay = uptimeMillis()
                val saveNote = Note(note, scoreStartTime, startPlay, endPlay)
                score.add(saveNote)
                println("piano key up $it")
            }
            ft.add(view.fullTonePianoKeys.id, fullTonePianoKey, "note_$it")

      //      ft.add(view.pianoKeys.id,fullTonePianoKey,"note $it")
        }
        halfTones.forEach { it ->
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)
            var startPlay:Long = 0

            halfTonePianoKey.onKeyDown = { note ->
                startPlay = uptimeMillis()
                println("Piano key down $note")
            }

            halfTonePianoKey.onKeyUp = { note ->
                val endPlay = uptimeMillis()
                val saveNote = Note(note, scoreStartTime, startPlay, endPlay)
                score.add(saveNote)
                println("Piano key up $note")
            }

            // View is possibly not instanced (possibly move to "onViewCreated")
            ft.add(view.halfToneKeysLayout.id, halfTonePianoKey, "note_$it")

        }
        ft.commit()

        view.saveScoreBt.setOnClickListener {
            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)

            if (fileSaveConditions(fileName, path)) {
                // println(fileName)
                fileName = "$fileName.musikk"

                FileOutputStream(File(path, fileName), true).bufferedWriter().use { writer ->
                    // bufferedWriter lever her, men stenger ned utenfor
                    score.forEach {
                        writer.write("${it.toString()}\n")
                    }
                }
                Toast.makeText(this.activity, "Music score saved", Toast.LENGTH_SHORT).show()
                scoreStartTime = uptimeMillis()
                score.clear()

            }
        }

        return view
    }

    fun doesFileExist(filename: String, path: File?): Boolean {
        val extendedFileName = "$filename.musikk"
        val fileAndPath = File(path, extendedFileName)

        return fileAndPath.exists()
    }


    fun fileSaveConditions(filename:String, path: File?): Boolean{

        when {
            path == null -> {
                Toast.makeText(this.activity, "Missing directory path", Toast.LENGTH_SHORT).show()
                return false
            }
            filename.isEmpty() -> {
                Toast.makeText(this.activity, "File name is missing", Toast.LENGTH_SHORT).show()
                return false
            }
            doesFileExist(filename,path) -> {
                Toast.makeText(this.activity, "File already exists", Toast.LENGTH_SHORT).show()
                return false
            }
            score.count() <= 0 -> {
                Toast.makeText(this.activity, "No notes have been played", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }
}