package xdisk.client.data;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import xdisk.VirtualFolder;
import xdisk.VirtualResource;
import xdisk.client.core.VirtualDisk;

public class TreeModel {
	public static void tree(DefaultMutableTreeNode parent, VirtualDisk disk){
		try {
			//System.out.println(parent.toString());
			LinkedList<VirtualResource> resources = new LinkedList<VirtualResource>();
			TreeNode[] paths = parent.getPath();
			String path="";
			//System.out.println(paths.length);
			if(paths.length==1){
				path="/";
				resources.addAll(disk.getList(path));
			}
			else{
				for(int i=0;i<paths.length;i++){
					//System.out.println("paths "+paths[i]);
					if(i==0)
						path+=paths[i];
					else
						path+=paths[i]+"/";
				}
				resources.addAll(disk.getList(path));
			}
			System.out.println("path"+path);
			for(int i=0;i<resources.size();i++){
				if (((VirtualResource)resources.get(i)).isDirectory()) {
					VirtualFolder folder = (VirtualFolder) resources.get(i);
					String[] dirs = folder.getPath().split("/");
					String nameDir= dirs[dirs.length-1];
					//System.out.println("nameDir "+nameDir);
					DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(nameDir);
					parent.add(nodo);
					tree(nodo,disk);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Errore nel recupero della struttura del Disco Virtuale", "Errore", JOptionPane.ERROR_MESSAGE);
		}	
	}
}
