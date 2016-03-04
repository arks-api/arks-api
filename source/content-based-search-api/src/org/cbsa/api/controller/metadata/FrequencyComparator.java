package org.cbsa.api.controller.metadata;

import java.util.Comparator;

import org.cbsa.api.model.KeywordDetails;

public class FrequencyComparator implements Comparator<KeywordDetails> {

    @Override
    public int compare(KeywordDetails firstKeyword, KeywordDetails secondKeyword) {
        return -firstKeyword.getFrequency().compareTo(
                secondKeyword.getFrequency());
    }

}
