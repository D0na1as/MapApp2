package com.example.helpapp.InfoScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import com.example.helpapp.Data.Statistic;
import com.example.helpapp.InfoFragment;
import com.example.helpapp.MainActivity;
import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentOverallBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.WHITE;

public class OverallFragment extends Fragment {

    private FragmentOverallBinding binding;
    private PieChart pieChart;
    private Statistic data;
    private String descriptionKiti = ":";
    private int[] iconArray = {R.mipmap.box, R.mipmap.shoe_covers, R.mipmap.cap, R.mipmap.goggles,
            R.mipmap.suit, R.mipmap.mask, R.mipmap.gloves};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentOverallBinding.inflate(inflater, container, false);
        MainActivity activity = (MainActivity) getActivity();
        if (activity.dataStatistic!=null) {
            data = activity.dataStatistic;

            EditText title = binding.getRoot().findViewById(R.id.Antraste);
            title.setBackgroundColor(binding.getRoot().getSolidColor());

            pieChart = binding.getRoot().findViewById(R.id.chart);

            List<PieEntry> entries = getPieEntries();

            PieDataSet set = new PieDataSet(entries, "");

            set.setSliceSpace(3f);
            set.setIconsOffset(new MPPointF(0, 0));
            set.setSelectionShift(5f);
//         Outside values
            set.setLabel(null);
            set.setValueLinePart1OffsetPercentage(10f); /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
            set.setValueLineColor(ColorTemplate.COLOR_NONE);
            set.setValueTextColor(ColorTemplate.COLOR_NONE);
            set.setValueTextSize(13);

            int[] colors = new int[]{Color.RED, WHITE, Color.GREEN, Color.YELLOW, BLUE, Color.MAGENTA, Color.CYAN};
            set.setColors(colors, 150);

            createPieChart(set);
        }
        return binding.getRoot();
    }

    private void createPieChart(PieDataSet set) {
        PieData data = new PieData(set);
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setTextColor(WHITE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13);
        legend.setFormSize(15);
        pieChart.setExtraOffsets(25.f, 20.f, 20.f, 20.f);
        legend.setYOffset(10);
        legend.setXOffset(10);
        pieChart.setHoleRadius(30);
        pieChart.getDescription().setEnabled(false);
        pieChart.setTransparentCircleRadius(0);
        pieChart.setHoleColor(binding.getRoot().getSolidColor());
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationEnabled(false);
        pieChart.animateX(600);
        pieChart.animateY(600);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String formated = String.format("%d",(int) e.getY());
                Toast.makeText(getContext(), formated, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    @NotNull
    private List<PieEntry> getPieEntries() {
        List<PieEntry> entries = new ArrayList<>();
        int[] arrayData = createArray(data);
        int sum = Sum(data);
        int kiti = 0;
        for (int i = 1; i < 7; i++) {
            if ((sum / arrayData[i - 1]) > 15 && arrayData[i - 1] > 0) {
                if (kiti == 0) {
                    descriptionKiti = descriptionKiti + (" " + MainActivity.itemDescription(String.valueOf(i)) + ".");
                } else {
                    descriptionKiti = descriptionKiti.substring(0, descriptionKiti.length() - 1);
                    descriptionKiti = descriptionKiti + (", " + MainActivity.itemDescription(String.valueOf(i)) + ".");
                }
                kiti += arrayData[i - 1];
            } else {
                entries.add(new PieEntry((arrayData[i - 1]), MainActivity.itemDescription(String.valueOf(i)), getContext().getDrawable(iconArray[i])));
            }
        }
        if (kiti != 0) {
            entries.add(new PieEntry(kiti, "Kiti" + descriptionKiti, getContext().getDrawable(iconArray[0])));
        }
        return entries;
    }

    private int Sum(Statistic data) {
        int sum = 0;
        for (int i : createArray(data)) {
            sum += i;
        }
        return sum;
    }

    private int[] createArray(Statistic data) {
        return new int[]{data.getShoe_covers(), data.getCaps(), data.getGoggles(), data.getSuits(), data.getMasks(), data.getGloves()};
    }

}
