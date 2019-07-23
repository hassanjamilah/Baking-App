package com.andalus.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.andalus.bakingapp.Fragments.IngredientsFragment;
import com.andalus.bakingapp.R;

public class MyBakingWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        String f = intent.getStringExtra("h1") ;

        return new MyWidgetListViewFactory(getApplicationContext());
    }

    class MyWidgetListViewFactory implements RemoteViewsFactory{

        private Context mContext ;


        public MyWidgetListViewFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

           if (IngredientsFragment.mIngredients ==  null || IngredientsFragment.mIngredients.size() == 0 ){
               return  0 ;
           }else  {

            return IngredientsFragment.mIngredients.size();
           }
        }

        @Override
        public RemoteViews getViewAt(int position) {

           RemoteViews views = new RemoteViews(getApplicationContext().getPackageName() , R.layout.my_baking_app_widget) ;
           if (IngredientsFragment.mIngredients == null || IngredientsFragment.mIngredients.size() == 0 ){

           }else {
               views.setTextViewText(R.id.appwidget_text ,IngredientsFragment.mIngredients.get(position).getIngredientName());

               String smallText = IngredientsFragment.mIngredients.get(position).getQuantity() + " " + IngredientsFragment.mIngredients.get(position).getMeasure() ;
               views.setTextViewText(R.id.appwidget_text_smal ,smallText);

           }




           return  views ;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
