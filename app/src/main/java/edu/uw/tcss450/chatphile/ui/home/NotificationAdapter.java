package edu.uw.tcss450.chatphile.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.tcss450.chatphile.R;

/**
 * RecyclerViewAdapter for notifications
 * @author Edwin
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public List<NotificationModel> arr_Messages;

    public NotificationAdapter(List<NotificationModel> nm) {
        arr_Messages = nm;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.noti_name);
            message =itemView.findViewById(R.id.noti_message);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View notificationView = inflater.inflate(R.layout.fragment_home_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(notificationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel notificationModel = arr_Messages.get(position);
        TextView textView1 = holder.name;
        textView1.setText(notificationModel.name);

        TextView textView2 = holder.message;
        textView2.setText(notificationModel.message);
    }

    @Override
    public int getItemCount() {
        return arr_Messages.size();
    }
}
