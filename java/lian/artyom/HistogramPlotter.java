package lian.artyom;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static lian.artyom.RMethods.Result3D;
import static lian.artyom.RMethods.Tuple;

/**
 * Created by artem on 27.10.15.
 */
public class HistogramPlotter extends ApplicationFrame
{

    private Result3D result;

    public HistogramPlotter(String title)
    {
        super(title);
    }

    public HistogramPlotter(String title, Result3D result)
    {
        this(title);
        this.result = result;
    }


    private JFreeChart createChart(final XYDataset dataset)
    {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart Demo 6",      // chart title
                "X",                      // x axis label
                "Y",                      // y axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);

        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesShape(0, new Rectangle(2, 2));
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    public void setResult(Result3D result)
    {
        if (this.result == null)
        {
            this.result = result;
        }
    }

    public void plot(byte TYPE, int width, int height)
    {
        switch (TYPE)
        {
            case HistogramBuilder.MAPPER_1_DIM:
                build1DimHist();
                return;
            case HistogramBuilder.MAPPER_2_DIM:
                throw new RuntimeException("not implemented yet");
            case HistogramBuilder.MAPPER_3_DIM:
                build3DimHist(width, height);
        }
        pack();
        RefineryUtilities.centerFrameOnScreen(this);
        setVisible(true);
    }

    private void build3DimHist(int width, int height)
    {
        Result3D tempResult = RMethodsUtils.sumResult3D(result, width, height);
        this.result = tempResult;
        build1DimHist();
    }

    private void build1DimHist()
    {
        Map<Tuple, Integer> resultMap = new HashMap<>();
        for (int i = 0; i < result.tuples.length; i++)
        {
//            if (resultMap.containsKey(result.tuples[i]))
//            {
//                resultMap.put(result.tuples[i], resultMap.get(result.tuples[i]) + result.values[i]);
//            } else
//            {
                resultMap.put(result.tuples[i], result.values[i]);
//            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries colors = new XYSeries("Colors");

        System.out.println("new size:" + resultMap.entrySet().size());

        for (Map.Entry<Tuple, Integer> entry : resultMap.entrySet())
        {
            colors.add(entry.getKey().z, entry.getValue());
        }
        dataset.addSeries(colors);

        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
        pack();
        setVisible(true);
    }
}
