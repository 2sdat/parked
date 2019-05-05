package dev.aidaco.parked.ViewModels;

import dev.aidaco.parked.Model.Entities.SpotData;

public interface SpotListClickListener {
    void onSpotClick(SpotData spotData);

    void onSpotLongClick(SpotData spotData);
}
