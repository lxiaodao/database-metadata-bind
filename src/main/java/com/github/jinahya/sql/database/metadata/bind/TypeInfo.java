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


import java.sql.DatabaseMetaData;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author Jin Kwon &lt;jinahya_at_gmail.com&gt;
 */
@XmlRootElement
@XmlType(
    propOrder = {
        "typeName", "dataType", "precision", "literalPrefix", "literalSuffix",
        "createParams", "nullable", "caseSensitive", "searchable",
        "unsignedAttribute", "fixedPrecScale", "autoIncrement", "localTypeName",
        "minimumScale", "maximumScale", "sqlDataType", "sqlDatetimeSub",
        "numPrecRadix"
    }
)
public class TypeInfo {


    // ---------------------------------------------------------------- metadata
    public Metadata getMetadata() {

        return metadata;
    }


    // ---------------------------------------------------------------- typeName
    public String getTypeName() {

        return typeName;
    }


    public void setTypeName(final String typeName) {

        this.typeName = typeName;
    }


    // ------------------------------------------------------------- getDataType
    public int getDataType() {

        return dataType;
    }


    public void setDataType(final int dataType) {

        this.dataType = dataType;
    }


    // --------------------------------------------------------------- precesion
    public int getPrecision() {

        return precision;
    }


    public void setPrecision(final int precision) {

        this.precision = precision;
    }


    // ----------------------------------------------------------- literalPrefix
    public String getLiteralPrefix() {

        return literalPrefix;
    }


    public void setLiteralPrefix(final String literalPrefix) {

        this.literalPrefix = literalPrefix;
    }


    // ----------------------------------------------------------- literalSuffix
    public String getLiteralSuffix() {

        return literalSuffix;
    }


    public void setLiteralSuffix(final String literalSuffix) {

        this.literalSuffix = literalSuffix;
    }


    // ------------------------------------------------------------ createParams
    public String getCreateParams() {

        return createParams;
    }


    public void setCreateParams(final String createParams) {

        this.createParams = createParams;
    }


    // ---------------------------------------------------------------- nullable
    public short getNullable() {

        return nullable;
    }


    public void setNullable(final short nullable) {

        this.nullable = nullable;
    }


    // ----------------------------------------------------------- caseSensitive
    public boolean getCaseSensitive() {
        return caseSensitive;
    }


    public void setCaseSensitive(final boolean caseSensitive) {

        this.caseSensitive = caseSensitive;
    }


    // -------------------------------------------------------------- searchable
    public short getSearchable() {
        return searchable;
    }


    public void setSearchable(final short searchable) {

        this.searchable = searchable;
    }


    // ------------------------------------------------------- unsignedAttribute
    public boolean getUnsignedAttribute() {

        return unsignedAttribute;
    }


    public void setUnsignedAttribute(final boolean unsignedAttribute) {

        this.unsignedAttribute = unsignedAttribute;
    }


    // ---------------------------------------------------------- fixedPrecScale
    public boolean getFixedPrecScale() {

        return fixedPrecScale;
    }


    public void setFixedPrecScale(final boolean fixedPrecScale) {

        this.fixedPrecScale = fixedPrecScale;
    }


    // ----------------------------------------------------------- autoIncrement
    public boolean getAutoIncrement() {

        return autoIncrement;
    }


    public void setAutoIncrement(final boolean autoIncrement) {

        this.autoIncrement = autoIncrement;
    }


    // ----------------------------------------------------------- localTypeName
    public String getLocalTypeName() {

        return localTypeName;
    }


    public void setLocalTypeName(final String localTypeName) {

        this.localTypeName = localTypeName;
    }


    // ------------------------------------------------------------ minimumScale
    public short getMinimumScale() {

        return minimumScale;
    }


    public void setMinimumScale(final short minimumScale) {

        this.minimumScale = minimumScale;
    }


    // ------------------------------------------------------------ maximumScale
    public short getMaximumScale() {

        return maximumScale;
    }


    public void setMaximumScale(final short maximumScale) {

        this.maximumScale = maximumScale;
    }


    // ------------------------------------------------------------ numPrecRadix
    public int getNumPrecRadix() {

        return numPrecRadix;
    }


    public void setNumPrecRadix(final int numPrecRadix) {

        this.numPrecRadix = numPrecRadix;
    }


    /**
     * parent metadata.
     */
    @XmlTransient
    private Metadata metadata;


    /**
     * Type name.
     */
    @Label("TYPE_NAME")
    @XmlElement(required = true)
    String typeName;


    /**
     * SQL data type from {@link java.sql.Types}.
     */
    @Label("DATA_TYPE")
    @XmlElement(required = true)
    int dataType;


    /**
     * maximum precision.
     */
    @Label("PRECISION")
    @XmlElement(required = true)
    int precision;


    /**
     * prefix used to quote a literal (may be {@code null}).
     */
    @Label("LITERAL_PREFIX")
    @XmlElement(nillable = true, required = true)
    @NillableBySpecification
    String literalPrefix;


    /**
     * suffix used to quote a literal (may be {@code null}).
     */
    @Label("LITERAL_SUFFIX")
    @XmlElement(nillable = true, required = true)
    String literalSuffix;


    /**
     * parameters used in creating the type (may be {@code null}).
     */
    @Label("CREATE_PARAMS")
    @XmlElement(nillable = true, required = true)
    String createParams;


    /**
     * can you use NULL for this type.
     * <ul>
     * <li>{@link DatabaseMetaData#typeNoNulls} - does not allow NULL
     * values</li>
     * <li>{@link DatabaseMetaData#typeNullable} - allows NULL values</li>
     * <li>{@link DatabaseMetaData#typeNullableUnknown} - nullability
     * unknown</li>
     * </ul>
     */
    @Label("NULLABLE")
    @XmlElement(required = true)
    short nullable;


    /**
     * is it case sensitive..
     */
    @Label("CASE_SENSITIVE")
    @XmlElement(required = true)
    boolean caseSensitive;


    /**
     * can you use "WHERE" based on this type:
     * <ul>
     * <li>{@link DatabaseMetaData#typePredNone} - No support</li>
     * <li>{@link DatabaseMetaData#typePredChar} - Only supported with WHERE ..
     * LIKE</li>
     * <li>{@link DatabaseMetaData#typePredBasic} - Supported except for WHERE
     * .. LIKE</li>
     * <li>{@link DatabaseMetaData#typeSearchable} - Supported for all WHERE
     * ..</li>
     * </ul>
     */
    @Label("SEARCHABLE")
    @XmlElement(required = true)
    short searchable;


    /**
     * is it unsigned.
     */
    @Label("UNSIGNED_ATTRIBUTE")
    @XmlElement(required = true)
    boolean unsignedAttribute;


    /**
     * can it be a money value.
     */
    @Label("FIXED_PREC_SCALE")
    @XmlElement(required = true)
    boolean fixedPrecScale;


    /**
     * can it be used for an auto-increment value.
     */
    @Label("AUTO_INCREMENT")
    @XmlElement(required = true)
    boolean autoIncrement;


    /**
     * localized version of type name (may be {@code null}).
     */
    @Label("LOCAL_TYPE_NAME")
    @XmlElement(nillable = true, required = true)
    @NillableBySpecification
    String localTypeName;


    /**
     * minimum scale supported.
     */
    @Label("MINIMUM_SCALE")
    @XmlElement(required = true)
    short minimumScale;


    /**
     * maximum scale supported.
     */
    @Label("MAXIMUM_SCALE")
    @XmlElement(required = true)
    short maximumScale;


    /**
     * unused.
     */
    @Label("SQL_DATA_TYPE")
    @NotUsed
    @XmlElement(required = true)
    int sqlDataType;


    /**
     * unused.
     */
    @Label("SQL_DATA_TYPE")
    @NotUsed
    @XmlElement(required = true)
    int sqlDatetimeSub;


    /**
     * usually 2 or 10.
     */
    @Label("NUM_PREC_RADIX")
    @XmlElement(required = true)
    int numPrecRadix;


}
