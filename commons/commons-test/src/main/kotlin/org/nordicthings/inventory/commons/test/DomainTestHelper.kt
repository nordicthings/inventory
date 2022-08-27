package org.nordicthings.inventory.commons.test

import org.junit.jupiter.api.Assertions.*

open class DomainTestHelper {

    fun checkEqualsContract(orgInstance: Any, equalInstance: Any, anotherEqualInstance: Any, notEqualInstance: Any) {
        assertEquals(orgInstance, orgInstance, "Reflexivität verletzt")
        assertEquals(orgInstance == equalInstance, equalInstance == orgInstance, "Symmetrie verletzt")
        assertEquals(
            orgInstance == equalInstance && equalInstance == anotherEqualInstance,
            orgInstance == anotherEqualInstance, "Transitivität verletzt"
        )
        for (i in 0..999) {
            // Konsistenz per 1000facher Wiederholung zu testen ist zugegebenerweise etwas lame. Mir fällt aber nix besseres ein...
            assertEquals(orgInstance, equalInstance, "Konsistenz bei Gleichheit verletzt")
            assertNotEquals(orgInstance, notEqualInstance, "Konsistenz bei Ungleichheit verletzt")
        }
        assertFalse(orgInstance.equals(null), "Null-Ungleichheit nicht gegeben")
    }

    fun checkHashCodeContract(orgInstance: Any, equalInstance: Any) {
        val hashCode = orgInstance.hashCode()
        for (i in 0..999) {
            assertEquals(hashCode, orgInstance.hashCode(), "Konsistenz bei Gleichheit verletzt")
        }
        assertEquals(
            orgInstance == equalInstance,
            orgInstance.hashCode() == equalInstance.hashCode(),
            "Hashcode gleicher Objekte ergeben unterschiedliche Hashcodes"
        )
    }

    fun <T : Comparable<T>> checkComparableToContract(
        orgInstance: T,
        equalInstance: T,
        precedingInstance: T,
        followingInstance: T
    ) {
        assertEquals(
            -1,
            Integer.signum(precedingInstance.compareTo(orgInstance)),
            "Vergleich kleinere Instanz mit größerer Instanz sollte kleiner 0 sein"
        )
        assertEquals(
            1,
            Integer.signum(followingInstance.compareTo(orgInstance)),
            "Vergleich größere Instanz mit kleinerer Instanz sollte größer 0 sein"
        )
        assertEquals(
            -Integer.signum(followingInstance.compareTo(precedingInstance)),
            Integer.signum(precedingInstance.compareTo(equalInstance)),
            "Inverse Umkehrung ist nicht gegeben"
        )
        assertEquals(
            orgInstance.compareTo(equalInstance),
            equalInstance.compareTo(orgInstance),
            "Reflexivität ist nicht gegeben"
        )
        assertEquals(
            precedingInstance > orgInstance && orgInstance > followingInstance,
            precedingInstance > followingInstance,
            "Transitivität verletzt"
        )
        assertEquals(
            orgInstance.compareTo(equalInstance) == 0,
            Integer.signum(orgInstance.compareTo(followingInstance)) == Integer.signum(
                equalInstance.compareTo(followingInstance)
            ),
            "Folgerung aus Gleichheit für z>0 verletzt"
        )
        assertEquals(
            orgInstance.compareTo(equalInstance) == 0,
            Integer.signum(orgInstance.compareTo(precedingInstance)) == Integer.signum(
                equalInstance.compareTo(precedingInstance)
            ),
            "Folgerung aus Gleichheit für z<0 verletzt"
        )
    }

    fun <T : Comparable<T>> checkComparableToConsistence(orgInstance: T, equalInstance: T) {
        assertEquals(0, orgInstance.compareTo(equalInstance), "Gleiche Exemplare müssen 0 ergeben")
    }

}
