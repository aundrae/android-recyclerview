package com.example.relearning

class Contract(val name: String, val isOnline: Boolean) {

    companion object {
        private var lastContactId = 0
        fun createContactsList(numContacts: Int): ArrayList<Contract> {
            val contacts = ArrayList<Contract>()
            for (i in 1..numContacts) {
                contacts.add(Contract("Person " + ++lastContactId, i <= numContacts / 2))
            }
            return contacts
        }
    }
}