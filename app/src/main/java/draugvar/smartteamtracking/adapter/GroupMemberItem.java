package draugvar.smartteamtracking.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;

import draugvar.smartteamtracking.R;
import draugvar.smartteamtracking.data.User;

public class GroupMemberItem extends AbstractItem<GroupMemberItem, GroupMemberItem.ViewHolder> {
    private static final ViewHolderFactory<? extends ViewHolder> FACTORY = new ItemFactory();
    public User user;

    public GroupMemberItem(User user){
        this.user = user;
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.group_member_item;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.group_member_item;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(ViewHolder viewHolder) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder);
        //bind our data
        //set the text for the name
        viewHolder.name.setText(user.getName());
        //set the text for the description or hide
        viewHolder.description.setText(user.getSurname());
        //set the text for status of parties
        if(user.getBeacon() != null)
        {
            viewHolder.status.setText(user.getBeacon().getName());

        } else if(!user.isCurrentGPS()){
            viewHolder.card.setEnabled(false);
            String beacon = "Out of Range";
            viewHolder.status.setText(beacon);
        } else {
            String beacon = "No Beacon";
            viewHolder.status.setText(beacon);
        }
        //set the text for initials // to do elaborate!
        if(!user.getName().isEmpty()) {
            String init = user.getName().substring(0, 1) + user.getSurname().substring(0, 1);
            viewHolder.initials.setText(init);
        }
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView description;
        protected TextView status;
        protected TextView initials;
        protected LinearLayout card;

        public ViewHolder(View view) {
            super(view);
            this.name = (TextView) view.findViewById(R.id.group_member_name);
            this.description = (TextView) view.findViewById(R.id.group_member_description);
            this.status = (TextView) view.findViewById(R.id.group_member_status);
            this.initials = (TextView) view.findViewById(R.id.group_member_initials);
            this.card = (LinearLayout) view.findViewById(R.id.friend_card);
        }
    }

    protected static class ItemFactory implements ViewHolderFactory<ViewHolder> {
        @Override
        public ViewHolder create(View v) {
            return new ViewHolder(v);
        }
    }

    @Override
    public ViewHolderFactory<? extends ViewHolder> getFactory() {
        return FACTORY;
    }
}