package com.bridgeit.TodoApp.model;

public class ProfileData {
		private String url;
		private boolean is_silhouette;
		
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public boolean isIs_silhouette() {
			return is_silhouette;
		}
		public void setIs_silhouette(boolean is_silhouette) {
			this.is_silhouette = is_silhouette;
		}
		
		@Override
		public String toString() {
			return "ProfileData [url=" + url + ", is_silhouette=" + is_silhouette + "]";
		}
		
		

		
}
