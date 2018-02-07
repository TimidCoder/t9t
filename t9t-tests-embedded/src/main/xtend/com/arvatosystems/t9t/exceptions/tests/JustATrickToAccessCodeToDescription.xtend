package com.arvatosystems.t9t.exceptions.tests

import de.jpaw.util.ApplicationException

class JustATrickToAccessCodeToDescription extends ApplicationException {

    new(int errorCode) {
        super(errorCode)
    }

    def static boolean validateAllExceptions() {
        var boolean isOk = true
        for (e: codeToDescription.entrySet) {
            if (e.key != 0) {
                val classification = e.key / CLASSIFICATION_FACTOR
                if (classification < 1 || classification > 9) {
                    println('''Code «e.key» is not of 9 digit size as required. Text: «e.value»''')
                    isOk = false
                }
            }
        }
        return isOk
    }

    def static void listAllExceptions() {
        val all = codeToDescription.entrySet
        all.sortBy[key].forEach[ println('''«key»: «value»''') ]
        println('''Listed «all.size» exceptions''')
    }
}
