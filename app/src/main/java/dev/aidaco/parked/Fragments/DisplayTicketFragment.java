package dev.aidaco.parked.Fragments;

import android.view.View;

import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.DisplayTicketViewModel;

public class DisplayTicketFragment extends BaseFragment<DisplayTicketViewModel> {


    @Override
    public void initViews(View view) {
        // TODO implement displayticketfragment
    }

    @Override
    public void createCallbacks() {

    }

    @Override
    public Class<DisplayTicketViewModel> getViewModelClass() {
        return DisplayTicketViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_display_ticket;
    }
}
