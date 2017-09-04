/*
 * Copyright 2013 Jin Kwon <onacit at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jinahya.sql.database.metadata.bind;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * An entity class for binding the result of
 * {@link java.sql.DatabaseMetaData#getUDTs(java.lang.String, java.lang.String, java.lang.String, int[]) getUDTs(catalog, schemaPattern, typeNamePattern, types)}.
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@XmlRootElement
@XmlType(propOrder = {
    "typeName", "className", "dataType", "remarks", "baseType",
    // ---------------------------------------------------------------------
    "attributes", "superTypes"
})
public class UDT {

    // -------------------------------------------------------------------------
    @Override
    public String toString() {
        return super.toString() + "{"
               + "typeCat=" + typeCat
               + ",typeSchem=" + typeSchem
               + ",typeName=" + typeName
               + ",className=" + className
               + ",dataType=" + dataType
               + ",remarks=" + remarks
               + ",baseType=" + baseType
               + "}";
    }

    // ----------------------------------------------------------------- typeCat
    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(final String typeCat) {
        this.typeCat = typeCat;
    }

    // --------------------------------------------------------------- typeSchem
    public String getTypeSchem() {
        return typeSchem;
    }

    public void setTypeSchem(final String typeSchem) {
        this.typeSchem = typeSchem;
    }

    // ---------------------------------------------------------------- typeName
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    // --------------------------------------------------------------- className
    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    // ---------------------------------------------------------------- dataType
    public int getDataType() {
        return dataType;
    }

    public void setDataType(final int dataType) {
        this.dataType = dataType;
    }

    // ----------------------------------------------------------------- remarks
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    // ---------------------------------------------------------------- baseType
    public Short getBaseType() {
        return baseType;
    }

    public void setBaseType(final Short baseType) {
        this.baseType = baseType;
    }

    // -------------------------------------------------------------- attributes
    public List<Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<Attribute>();
        }
        return attributes;
    }

    // -------------------------------------------------------------- superTypes
    public List<SuperType> getSuperTypes() {
        if (superTypes == null) {
            superTypes = new ArrayList<SuperType>();
        }
        return superTypes;
    }

    // -------------------------------------------------------------------------
    @Labeled("TYPE_CAT")
    @Nillable
    @XmlAttribute
    private String typeCat;

    @Labeled("TYPE_SCHEM")
    @Nillable
    @XmlAttribute
    private String typeSchem;

    @Labeled("TYPE_NAME")
    @XmlElement(required = true)
    private String typeName;

    @Labeled("CLASS_NAME")
    @XmlElement(required = true)
    private String className;

    @Labeled("DATA_TYPE")
    @XmlElement(required = true)
    private int dataType;

    @Labeled("REMARKS")
    @XmlElement(required = true)
    private String remarks;

    @Labeled("BASE_TYPE")
    @Nillable
    @XmlElement(nillable = true, required = true)
    private Short baseType;

    @Invokable(name = "getAttributes",
            types = {String.class, String.class, String.class, String.class},
            args = {
                @Literals({":typeCat", ":typeSchem", ":typeName", "null"})
            }
    )
    @XmlElementRef
    private List<Attribute> attributes;

    @Invokable(name = "getSuperTypes",
            types = {String.class, String.class, String.class},
            args = {
                @Literals({":typeCat", ":typeSchem", ":typeName"})
            }
    )
    @XmlElementRef
    private List<SuperType> superTypes;
}
