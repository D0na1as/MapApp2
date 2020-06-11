package com.example.helpapp.InfoScreen;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.helpapp.R;
import com.example.helpapp.databinding.FragmentOverallBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.WHITE;

public class OverallFragment extends Fragment {

    private FragmentOverallBinding binding;
    private PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentOverallBinding.inflate(inflater, container, false);

        EditText title = binding.getRoot().findViewById(R.id.Antraste);
        title.setBackgroundColor(binding.getRoot().getSolidColor());

        pieChart = binding.getRoot().findViewById(R.id.chart);

        Drawable cap = getContext().getDrawable(R.mipmap.cap);
        Drawable gloves = getContext().getDrawable(R.mipmap.gloves);
        Drawable googles = getContext().getDrawable(R.mipmap.goggles);
        Drawable suit = getContext().getDrawable(R.mipmap.suit);
        Drawable mask = getContext().getDrawable(R.mipmap.mask);
        Drawable shoe_covers = getContext().getDrawable(R.mipmap.shoe_covers);
        Drawable box = getContext().getDrawable(R.mipmap.box);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(5f, "Kažkas", box));
        //entries.add(new PieEntry(3f, "Pirštinės"));
        entries.add(new PieEntry(24.0f, "Akiniai", googles));
        entries.add(new PieEntry(30.8f, "Kaukės", mask));
        entries.add(new PieEntry(30.8f, "Batų maišai", shoe_covers));
        entries.add(new PieEntry(30.8f, "Vienkartiniai kostiumai", suit));
        PieDataSet set = new PieDataSet(entries, "Election Results");
        set.setSliceSpace(3f);

        set.setIconsOffset(new MPPointF(0, 0));
        set.setSelectionShift(5f);
//         Outside values
        set.setLabel(null);


//        set.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        set.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        set.setValueLinePart1OffsetPercentage(10f); /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
//        set.setValueLinePart1Length(0.5f); /** When valuePosition is OutsideSlice, indicates length of first half of the line */
//        set.setValueLinePart2Length(0.25f); /** When valuePosition is OutsideSlice, indicates length of second half of the line */
        set.setValueLineColor(ColorTemplate.COLOR_NONE);
        set.setValueTextColor(ColorTemplate.COLOR_NONE);
        set.setValueTextSize(13);

        int[] colors = new int[] {Color.RED, WHITE, Color.GREEN, Color.YELLOW, BLUE, Color.MAGENTA};
        set.setColors(colors, 150);

        PieData data = new PieData(set);
        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setTextColor(WHITE);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(13);
        legend.setFormSize(15);
        pieChart.setExtraOffsets(20.f, 20.f, 20.f, 20.f);
        legend.setYOffset(35);
        legend.setXOffset(10);
        pieChart.setHoleRadius(30);
        //pieChart.setDrawHoleEnabled(false);
        pieChart.setTransparentCircleRadius(0);
//        pieChart.setTransparentCircleAlpha(255);
//        pieChart.setTransparentCircleColor(BLACK);
        pieChart.setHoleColor(binding.getRoot().getSolidColor());
        pieChart.setDrawEntryLabels(false);
        pieChart.setRotationEnabled(false);
        pieChart.animateX(600);
        pieChart.animateY(600);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                double a = e.getY();
                String asd = "" + a;
                Toast.makeText(getContext(), asd, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

//        set.setValueFormatter(new PercentFormatter(pieChart));
//        pieChart.setUsePercentValues(true);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh

        return binding.getRoot();
    }
}
