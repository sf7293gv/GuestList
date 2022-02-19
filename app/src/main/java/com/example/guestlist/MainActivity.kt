package com.example.guestlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val LAST_GUEST_NAME_KEY = "last-guest-name-bundle-key"

class MainActivity : AppCompatActivity() {

    private lateinit var addGuestButton: Button
    private lateinit var removeGuestsButton: Button
    private lateinit var newGuestEditText: EditText
    private lateinit var guestList: TextView
    private lateinit var lastGuestAdded: TextView

//    val guestNames = mutableListOf<String>()
    private val guestListViewModel: GuestListViewModel by lazy {
    // lazy initialization - lambda won't be called until guestListViewModel is used
        ViewModelProvider(this).get(GuestListViewModel::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGuestButton = findViewById(R.id.add_guest_button)
        removeGuestsButton = findViewById(R.id.remove_guests_button)
        newGuestEditText = findViewById(R.id.new_guest_input)
        guestList = findViewById(R.id.list_of_guests)
        lastGuestAdded = findViewById(R.id.last_guest_added)

        addGuestButton.setOnClickListener {
            addNewGuest()
        }

        removeGuestsButton.setOnClickListener() {
            removeAllGuests()
        }



        val savedLastGuestMessage = savedInstanceState?.getString(LAST_GUEST_NAME_KEY)
        lastGuestAdded.text = savedLastGuestMessage

        updateGuestList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_GUEST_NAME_KEY, lastGuestAdded.text.toString())
    }

    private fun removeAllGuests() {
        guestListViewModel.delete()
        updateGuestList()
    }

    private fun addNewGuest() {
        val newGuestName = newGuestEditText.text.toString()
        if (newGuestName.isNotBlank()) {
//            guestNames.add(newGuestName)
            guestListViewModel.addGuest(newGuestName)
            updateGuestList()
            newGuestEditText.text.clear()
            lastGuestAdded.text = getString(R.string.Last_guest_message, newGuestName)
        } else {

        }
    }
    private fun updateGuestList() {
//        val guestDisplay = guestNames.sorted().joinToString(separator = "\n")
        val guests = guestListViewModel.getSortedGuestNames()
        val guestDisplay = guests.joinToString(separator = "\n")
        guestList.text = guestDisplay
    }
}