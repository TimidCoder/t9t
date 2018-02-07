package com.arvatosystems.t9t.doc.be.tests

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.doc.DocComponentDTO
import de.jpaw.bonaparte.api.ColumnCollector
import de.jpaw.bonaparte.pojos.ui.UIDefaults
import de.jpaw.bonaparte.util.ToStringHelper
import org.junit.Test

class GridConfigtest {
    public static final UIDefaults MY_DEFAULTS = new UIDefaults => [
        renderMaxArrayColumns = 5
        widthObject = 160
        widthCheckbox = 32
        widthEnum = 160
        widthEnumset = 300
        widthOffset = 8    // offset for all fields
        widthPerCharacter = 8  // average character width
        widthMax = 400
    ]

    @Test
    def void testColumnCollector() {
        val cc = new ColumnCollector(MY_DEFAULTS)
        cc.addToColumns(FullTrackingWithVersion.class$MetaData)
        println('''Columns are «ToStringHelper.toStringML(cc.columns)»''')
    }

    @Test
    def void testColumnCollector2() {
        val cc = new ColumnCollector(MY_DEFAULTS)
        cc.addToColumns(DocComponentDTO.class$MetaData)
        println('''Columns are «ToStringHelper.toStringML(cc.columns)»''')
    }
}
