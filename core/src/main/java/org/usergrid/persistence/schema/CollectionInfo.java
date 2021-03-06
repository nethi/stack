/*******************************************************************************
 * Copyright (c) 2010, 2011 Ed Anuff and Usergrid, all rights reserved.
 * http://www.usergrid.com
 * 
 * This file is part of Usergrid Core.
 * 
 * Usergrid Core is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Usergrid Core is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Usergrid Core. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package org.usergrid.persistence.schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.usergrid.persistence.annotations.EntityCollection;

public class CollectionInfo {

	private String name;
	private EntityInfo container;

	private boolean indexingDynamicProperties;
	private boolean indexingDynamicDictionaries;
	private String linkedCollection;
	private Set<String> propertiesIndexed = new TreeSet<String>(
			String.CASE_INSENSITIVE_ORDER);
	private boolean publicVisible = true;
	private final Set<String> dictionariesIndexed = new TreeSet<String>(
			String.CASE_INSENSITIVE_ORDER);
	private Set<String> subkeys = new LinkedHashSet<String>();
	private String type;
	private boolean reversed;
	private boolean includedInExport = true;
	private String sort;

	public CollectionInfo() {
	}

	public CollectionInfo(EntityCollection collectionAnnotation) {
		setIndexingDynamicProperties(collectionAnnotation
				.indexingDynamicProperties());
		setIndexingDynamicDictionaries(collectionAnnotation
				.indexingDynamicDictionaries());
		setLinkedCollection(collectionAnnotation.linkedCollection());
		setPropertiesIndexed(new LinkedHashSet<String>(
				Arrays.asList(collectionAnnotation.propertiesIndexed())));
		setPublic(collectionAnnotation.publicVisible());
		setDictionariesIndexed(new LinkedHashSet<String>(
				Arrays.asList(collectionAnnotation.dictionariesIndexed())));
		setSubkeys(Arrays.asList(collectionAnnotation.subkeys()));
		setType(collectionAnnotation.type());
		setReversed(collectionAnnotation.reversed());
		setIncludedInExport(collectionAnnotation.includedInExport());
		setSort(collectionAnnotation.sort());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if ("".equals(type)) {
			type = null;
		}
		this.type = type;
	}

	public boolean isPropertyIndexed(String propertyName) {
		return propertiesIndexed.contains(propertyName);
	}

	public boolean hasIndexedProperties() {
		return !propertiesIndexed.isEmpty();
	}

	public Set<String> getPropertiesIndexed() {
		return propertiesIndexed;
	}

	public void setPropertiesIndexed(Set<String> propertiesIndexed) {
		this.propertiesIndexed = new TreeSet<String>(
				String.CASE_INSENSITIVE_ORDER);
		this.propertiesIndexed.addAll(propertiesIndexed);
	}

	public boolean isDictionaryIndexed(String propertyName) {
		return dictionariesIndexed.contains(propertyName);
	}

	public Set<String> getDictionariesIndexed() {
		return dictionariesIndexed;
	}

	public void setDictionariesIndexed(Set<String> dictionariesIndexed) {
		dictionariesIndexed = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		dictionariesIndexed.addAll(dictionariesIndexed);
	}

	public boolean isIndexingDynamicProperties() {
		return indexingDynamicProperties;
	}

	public void setIndexingDynamicProperties(boolean indexingProperties) {
		indexingDynamicProperties = indexingProperties;
	}

	public boolean isIndexingDynamicDictionaries() {
		return indexingDynamicDictionaries;
	}

	public void setIndexingDynamicDictionaries(
			boolean indexingDynamicDictionaries) {
		this.indexingDynamicDictionaries = indexingDynamicDictionaries;
	}

	public String getLinkedCollection() {
		return linkedCollection;
	}

	public void setLinkedCollection(String linkedCollection) {
		if ("".equals(linkedCollection)) {
			linkedCollection = null;
		}
		this.linkedCollection = linkedCollection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EntityInfo getContainer() {
		return container;
	}

	public void setContainer(EntityInfo entityInfo) {
		container = entityInfo;
	}

	public boolean isSubkeyProperty(String propertyName) {
		return subkeys.contains(propertyName);
	}

	public boolean hasSubkeys() {
		return !subkeys.isEmpty();
	}

	public Set<String> getSubkeySet() {
		return subkeys;
	}

	public List<String> getSubkeys() {
		return new ArrayList<String>(subkeys);
	}

	public void setSubkeys(List<String> s) {
		subkeys = new LinkedHashSet<String>();
		subkeys.addAll(s);
		makeSubkeyCombos();
	}

	List<String[]> subkeyCombinations = new ArrayList<String[]>();

	void makeSubkeyCombos() {
		subkeyCombinations = new ArrayList<String[]>();

		if (subkeys.size() > 0) {
			int combos = (1 << subkeys.size());
			// System.out.println(subkeys.size() + " elements = " +
			// combos
			// + " combos");
			for (int i = 1; i < combos; i++) {
				List<String> combo = new ArrayList<String>();
				int j = 0;
				for (String subkey : subkeys) {
					if (((1 << j) & i) != 0) {
						combo.add(subkey);
					} else {
						combo.add(null);
					}
					j++;
				}
				subkeyCombinations.add(combo.toArray(new String[0]));
			}
		}
	}

	public List<String[]> getSubkeyCombinations() {
		return subkeyCombinations;
	}

	public boolean isPublic() {
		return publicVisible;
	}

	public void setPublic(boolean publicVisible) {
		this.publicVisible = publicVisible;
	}

	public boolean isReversed() {
		return reversed;
	}

	public void setReversed(boolean reversed) {
		this.reversed = reversed;
	}

	public void setIncludedInExport(boolean includedInExport) {
		this.includedInExport = includedInExport;
	}

	public boolean isIncludedInExport() {
		return includedInExport;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		if ("".equals(sort)) {
			sort = null;
		}
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "CollectionInfo [name=" + name + ", container=" + container
				+ ", indexingDynamicProperties=" + indexingDynamicProperties
				+ ", indexingDynamicDictionaries="
				+ indexingDynamicDictionaries + ", linkedCollection="
				+ linkedCollection + ", propertiesIndexed=" + propertiesIndexed
				+ ", publicVisible=" + publicVisible + ", dictionariesIndexed="
				+ dictionariesIndexed + ", subkeys=" + subkeys + ", type="
				+ type + ", reversed=" + reversed + ", includedInExport="
				+ includedInExport + ", sort=" + sort + ", subkeyCombinations="
				+ subkeyCombinations + "]";
	}

}
