package jazz.comedystory.funnystories;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {
    private Context context;
    private ArrayList<ImageItem> data = new ArrayList<>();
    private int layoutResourceId;

    public GridViewAdapter(Context context2, int i, ArrayList<ImageItem> arrayList) {
        super(context2, i, arrayList);
        this.layoutResourceId = i;
        this.context = context2;
        this.data = arrayList;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((Activity) this.context).getLayoutInflater().inflate(this.layoutResourceId, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.imageTitle = (TextView) view.findViewById(R.id.text);
            viewHolder.image = (ImageView) view.findViewById(R.id.image);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ImageItem imageItem = this.data.get(i);
        viewHolder.imageTitle.setText(imageItem.getTitle());
        viewHolder.image.setImageBitmap(imageItem.getImage());
        return view;
    }

    static class ViewHolder {
        ImageView image;
        TextView imageTitle;

        ViewHolder() {
        }
    }
}
