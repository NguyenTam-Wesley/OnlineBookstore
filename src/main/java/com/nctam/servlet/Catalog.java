package com.nctam.servlet;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	public class Catalog {
		private static CatalogItem[] items;

		private static void loadItemsFromURL(String urlString) {
	        List<String[]> records = new ArrayList<>();
	        try {
	            URL url = new URL(urlString);
	            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] record = line.split("\\|");
	                records.add(record);
	            }
	            br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        items = new CatalogItem[records.size()];
	        int index = 0;
	        for (String[] record : records) {
	            String itemID = record[0];
	            String shortDescription = record[1];
	            String longDescription = record[2];
	            double cost = Double.parseDouble(record[3]);
	            items[index++] = new CatalogItem(itemID, shortDescription, longDescription, cost);
	        }
	    }
		
		static {
			loadItemsFromURL("http://localhost:8080/OBS/data/data.txt");
		}

		public static CatalogItem getItem(String itemID) {
			if (itemID == null) {
				return null;
			}
			for (CatalogItem item : items) {
				if (itemID.equals(item.getItemID())) {
					return item;
				}
			}
			return null;
		}
	}

