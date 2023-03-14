package com.crevado.fr.clientgqlapp_java;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.crevado.fr.clientgqlapp_java.databinding.FragmentFirstBinding;
import com.crevado.fr.clientgqlapp_java.util.AplClient;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        getUsers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /*
        Apollo Client stuff
     */
    private void getUsers() {
        AplClient.getmApolloClient()
                .query(UsersQuery.builder().build())
                .enqueue(new ApolloCall.Callback<UsersQuery.Data>() {
                    @Override
                    public void onResponse(@NonNull Response<UsersQuery.Data> response) {
                        Log.d("User", response.getData().users().get(0).name);

                        for (int i = 0; i < response.getData().users().size(); i++) {
                            Log.d("ID", response.getData().users().get(i).id);
                            Log.d("User", response.getData().users().get(i).name);
                            Log.d("Age", response.getData().users().get(i).age.toString());
                        }

                        //requireActivity().runOnUiThread(() -> binding.textviewFirst.setText(response.getData().users().get(0).name));

                    }

                    @Override
                    public void onFailure(@NonNull ApolloException e) {

                    }
                });
    }

}