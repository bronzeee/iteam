package com.houniao.iteam;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.*;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Administrator on 13-11-25.
 */
public class MapFragment extends Fragment {

    private MapView mMapView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        mMapView = (MapView) view.findViewById(R.id.bMapView);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setTraffic(true);
        MapController mMapController = mMapView.getController();
        GeoPoint point = new GeoPoint((int) (38.865 * 1E6), (int) (121.526 * 1E6));
        mMapController.setCenter(point);
        mMapController.setZoom(18);
        // 获取用于在地图上标注一个地理坐标点的图标
        Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
        GeoPoint p1 = new GeoPoint((int) (38.865 * 1E6), (int) (121.526 * 1E6));
        OverlayItem item1 = new OverlayItem(p1,"item1","item1");
        CustomOverlay itemOverlay = new CustomOverlay(drawable, mMapView);
        itemOverlay.addItem(item1);
        mMapView.getOverlays().clear();
        mMapView.getOverlays().add(itemOverlay);
        return view;
    }

    private class CustomOverlay extends ItemizedOverlay {
        public CustomOverlay(Drawable drawable, MapView mapView) {
            super(drawable, mapView);
        }

        @Override
        protected boolean onTap(int i) {
            return false;
        }

        @Override
        public boolean onTap(GeoPoint geoPoint, MapView mapView) {
            return true;
        }
    }


}
